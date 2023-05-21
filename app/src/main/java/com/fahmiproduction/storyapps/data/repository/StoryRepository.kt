package com.fahmiproduction.storyapps.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.*
import com.fahmiproduction.storyapps.api.ApiService
import com.fahmiproduction.storyapps.api.remote.*
import com.fahmiproduction.storyapps.api.response.*
import com.fahmiproduction.storyapps.data.database.AppDataSource
import com.fahmiproduction.storyapps.data.database.Preferences
import com.fahmiproduction.storyapps.data.database.StoryDatabase
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryRepository(
    private val apiService: ApiService,
    private val preferences: Preferences,
    private val storyDatabase: StoryDatabase,
    private val remoteDataSource: RemoteDataSource,

    ) : AppDataSource {

    companion object {
        @Volatile
        private var instance: StoryRepository? = null

        @JvmStatic
        fun getInstance(
            apiService: ApiService,
            preferences: Preferences,
            storyDatabase: StoryDatabase,
            remoteDataSource: RemoteDataSource,
        ): StoryRepository =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(apiService,
                    preferences,
                    storyDatabase,
                    remoteDataSource)
            }.also { instance = it }
    }

    override fun login(email: String, password: String): LiveData<LoginResponse> {
        val loginResponse = MutableLiveData<LoginResponse>()
        remoteDataSource.login(object : LoginCallback {
            override fun onLogin(login: LoginResponse) {
                loginResponse.postValue(login)
                if (!login.error) {
                    login.user?.let { preferences.setUser(it) }
                }
            }
        }, email, password)
        return loginResponse
    }

    override fun register(
        name: String,
        email: String,
        password: String,
    ): LiveData<RegisterResponse> {
        val registerResponse = MutableLiveData<RegisterResponse>()
        remoteDataSource.register(object : RegisterCallback {
            override fun onRegister(register: RegisterResponse) {
                registerResponse.postValue(register)
            }
        }, name, email, password)
        return registerResponse
    }

    override fun getUser(): LiveData<User> {
        val result = MutableLiveData<User>()
        result.postValue(preferences.getUser())
        return result
    }

    override fun deleteUser() {
        preferences.deleteUser()
    }

    override fun getStory(token: String): LiveData<PagingData<Story>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = 5),
            remoteMediator = StoryRemoteMediator(storyDatabase, apiService, token),
            pagingSourceFactory = {
                storyDatabase.storyDao().getStory()
            }

        ).liveData
    }

    override fun upload(
        token: String,
        file: MultipartBody.Part,
        description: RequestBody,
    ): LiveData<UploadResponse> {
        val response = MutableLiveData<UploadResponse>()
        remoteDataSource.uploud(object : UploadCallback {
            override fun onUpload(uploadResponse: UploadResponse) {
                response.postValue(uploadResponse)
            }
        }, token, file, description)
        return response
    }

    override fun getMaps(token: String): LiveData<StoryResponse> {
        val response = MutableLiveData<StoryResponse>()

        remoteDataSource.getMaps(object : MapsCallback {
            override fun onMaps(maps: StoryResponse) {
                response.postValue(maps)
            }
        }, token)
        return response
    }


}
