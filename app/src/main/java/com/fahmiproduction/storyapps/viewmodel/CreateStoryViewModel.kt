package com.fahmiproduction.storyapps.viewmodel

import androidx.lifecycle.ViewModel
import com.fahmiproduction.storyapps.data.repository.StoryRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class CreateStoryViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    fun upload(token: String, file: MultipartBody.Part, description: RequestBody) =
        storyRepository.upload(token, file, description)
}