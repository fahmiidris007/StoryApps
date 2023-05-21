package com.fahmiproduction.storyapps.api.remote

import com.fahmiproduction.storyapps.api.response.LoginResponse

interface LoginCallback {
    fun onLogin(loginResponse: LoginResponse)
}