package com.fahmiproduction.storyapps.data.database

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.fahmiproduction.storyapps.api.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface AppDataSource {
    fun login(email: String, password: String): LiveData<LoginResponse>
    fun register(name: String, email: String, password: String): LiveData<RegisterResponse>
    fun getUser(): LiveData<User>
    fun deleteUser()
    fun getStory(token: String): LiveData<PagingData<Story>>
    fun upload(
        token: String,
        file: MultipartBody.Part,
        description: RequestBody,
    ): LiveData<UploadResponse>

    fun getMaps(token: String): LiveData<StoryResponse>
}