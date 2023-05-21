package com.fahmiproduction.storyapps.api.remote

import com.fahmiproduction.storyapps.api.response.RegisterResponse

interface RegisterCallback {
    fun onRegister(registerResponse: RegisterResponse)
}