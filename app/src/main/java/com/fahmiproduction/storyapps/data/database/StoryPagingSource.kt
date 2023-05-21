package com.fahmiproduction.storyapps.data.database

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fahmiproduction.storyapps.api.ApiService
import com.fahmiproduction.storyapps.api.response.Story

class StoryPagingSource(private val apiService: ApiService) : PagingSource<Int, Story>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, Story>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Story> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val response = apiService.getStories(page, params.loadSize, "")
            LoadResult.Page(
                data = response.listStory!!,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.listStory.isEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

}
