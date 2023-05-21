package com.fahmiproduction.storyapps.api.remote

import com.fahmiproduction.storyapps.api.response.StoryResponse

interface MapsCallback {
    fun onMaps(maps:StoryResponse)
}