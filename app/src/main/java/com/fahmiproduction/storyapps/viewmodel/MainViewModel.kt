package com.fahmiproduction.storyapps.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fahmiproduction.storyapps.api.response.Story
import com.fahmiproduction.storyapps.data.repository.StoryRepository

class MainViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    fun getStory(token: String): LiveData<PagingData<Story>> =
        storyRepository.getStory(token).cachedIn(viewModelScope)

    fun deleteUser() = storyRepository.deleteUser()
}