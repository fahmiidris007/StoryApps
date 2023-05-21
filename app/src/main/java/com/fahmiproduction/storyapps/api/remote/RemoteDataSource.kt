package com.fahmiproduction.storyapps.api.remote

import com.fahmiproduction.storyapps.api.ApiConfig
import com.fahmiproduction.storyapps.api.response.LoginResponse
import com.fahmiproduction.storyapps.api.response.RegisterResponse
import com.fahmiproduction.storyapps.api.response.StoryResponse
import com.fahmiproduction.storyapps.api.response.UploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RemoteDataSource {

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null
        fun getInstance(): RemoteDataSource = instance ?: synchronized(this) {
            instance ?: RemoteDataSource()
        }
    }


    fun login(callback: LoginCallback, email: String, password: String) {
        val client = ApiConfig.getApiService().login(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { callback.onLogin(it) }
                } else {
                    val loginFailed = LoginResponse(
                        null, true, "Login failed"
                    )
                    callback.onLogin(loginFailed)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                val loginFailed = LoginResponse(null, true, t.message.toString())
                callback.onLogin(loginFailed)
            }
        })
    }

    fun register(callback: RegisterCallback, name: String, email: String, password: String) {
        val client = ApiConfig.getApiService().register(name, email, password)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>,
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { callback.onRegister(it) }
                } else {
                    val registerFailed = RegisterResponse(true, "Register failed"
                    )
                    callback.onRegister(registerFailed)
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                val registerFailed = RegisterResponse(true, t.message.toString())
                callback.onRegister(registerFailed)
            }
        })
    }

    fun uploud(
        callback: UploadCallback,
        token: String,
        file: MultipartBody.Part,
        description: RequestBody,
    ) {
        val client = ApiConfig.getApiService().upload(token, file, description)
        client.enqueue(object : Callback<UploadResponse> {
            override fun onResponse(
                call: Call<UploadResponse>,
                response: Response<UploadResponse>,
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error) {
                        callback.onUpload((responseBody))
                    } else {
                        callback.onUpload(
                            uploadResponse = UploadResponse(true, "Upload Failed")
                        )
                    }
                } else {
                    callback.onUpload(uploadResponse = UploadResponse(true, "Upload Failed"))

                }
            }

            override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                callback.onUpload(uploadResponse = UploadResponse(true, "Upload Failed"))
            }

        })
    }

    fun getMaps(
        callback: MapsCallback,
        token: String,
    ) {
        val client = ApiConfig.getApiService().getMaps(token)
        client.enqueue(object : Callback<StoryResponse> {
            override fun onResponse(
                call: Call<StoryResponse>,
                response: Response<StoryResponse>,
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    responseBody?.let { callback.onMaps(it) }
                } else {
                    callback.onMaps(StoryResponse(null, true, "Maps Failed"))

                }
            }

            override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
                callback.onMaps(StoryResponse(null, true, "Maps Failed"))
            }

        })
    }
}
