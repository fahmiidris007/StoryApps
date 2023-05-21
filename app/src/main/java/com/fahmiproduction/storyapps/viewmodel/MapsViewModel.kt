package com.fahmiproduction.storyapps.viewmodel

import androidx.lifecycle.ViewModel
import com.fahmiproduction.storyapps.data.repository.StoryRepository

class MapsViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    fun getMaps(token: String) = storyRepository.getMaps(token)
}