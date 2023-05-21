package com.fahmiproduction.storyapps

import com.fahmiproduction.storyapps.api.response.Story

object DataDummy {

    fun generateDummyStoryResponse(): List<Story> {
        val items: MutableList<Story> = arrayListOf()
        for (i in 0..100) {
            val story = Story(
                id = "Abcdefghijklmn",
                photoUrl = "htts://story-api.dicoding.dev/images/stories/photos-13913091.png",
                createdAt = "2023-05-09T07:21:17.589Z",
                name = "Kamu",
                description = "Ini deskripsi",
                lon = 0.00,
                lat = 0.00
            )
            items.add(story)
        }
        return items
    }
}