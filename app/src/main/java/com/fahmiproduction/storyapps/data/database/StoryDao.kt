package com.fahmiproduction.storyapps.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fahmiproduction.storyapps.api.response.Story

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(story: List<Story>)

    @Query("SELECT * FROM story")
    fun getStory(): PagingSource<Int, Story>

    @Query("DELETE FROM story WHERE 1")
    suspend fun delete()

}