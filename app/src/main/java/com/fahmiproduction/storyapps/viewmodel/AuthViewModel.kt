package com.fahmiproduction.storyapps.viewmodel

import androidx.lifecycle.ViewModel
import com.fahmiproduction.storyapps.data.repository.StoryRepository

class AuthViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    fun login(email: String, password: String) = storyRepository.login(email, password)
    fun register(name: String, email: String, password: String) =
        storyRepository.register(name, email, password)
    fun getUser() = storyRepository.getUser()
}