package com.fahmiproduction.storyapps.api

import com.fahmiproduction.storyapps.api.response.LoginResponse
import com.fahmiproduction.storyapps.api.response.RegisterResponse
import com.fahmiproduction.storyapps.api.response.StoryResponse
import com.fahmiproduction.storyapps.api.response.UploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<RegisterResponse>

    @GET("stories")
    suspend fun getStories(
        @Query("page") page: Int?,
        @Query("size") size: Int?,
        @Header("Authorization") token: String?,
    ): StoryResponse

    @Multipart
    @POST("stories")
    fun upload(
        @Header("Authorization") token: String?,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody?,
        @Part("lat") lat: RequestBody? = null,
        @Part("lon") lon: RequestBody? = null,
    ): Call<UploadResponse>

    @GET("stories")
    fun getMaps(
        @Header("Authorization") token: String?,
        @Query("location") page: Int = 1,
    ): Call<StoryResponse>
}