package com.fahmiproduction.storyapps.utils

import android.content.Context
import com.fahmiproduction.storyapps.api.ApiConfig
import com.fahmiproduction.storyapps.api.remote.RemoteDataSource
import com.fahmiproduction.storyapps.data.database.Preferences
import com.fahmiproduction.storyapps.data.database.StoryDatabase
import com.fahmiproduction.storyapps.data.repository.StoryRepository

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val apiConfig = ApiConfig.getApiService()
        val preferences = Preferences.getInstance(context)
        val storyDatabase = StoryDatabase.getDatabase(context)
        val remoteDataSource = RemoteDataSource.getInstance()
        return StoryRepository.getInstance(apiConfig, preferences, storyDatabase, remoteDataSource)
    }
}