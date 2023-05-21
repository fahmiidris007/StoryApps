package com.fahmiproduction.storyapps.api.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @field: SerializedName("loginResult")
    val user: User? = null,

    @field: SerializedName("error")
    val error: Boolean,

    @field: SerializedName("message")
    val message: String,
)
