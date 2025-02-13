package com.robotbot.avito.data.music.sources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.robotbot.avito.data.music.entities.SongDataEntity

class PagingDataSource(
    private val loader: MusicPageLoader,
    private val pageSize: Int
) : PagingSource<Int, SongDataEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SongDataEntity> {
        val pageIndex = params.key ?: 0

        return try {
            val songs = loader.invoke(pageIndex, params.loadSize)
            return LoadResult.Page(
                data = songs,
                prevKey = if (pageIndex == 0) null else pageIndex - 1,
                nextKey = if (songs.size == params.loadSize) pageIndex + (params.loadSize / pageSize) else null
            )
        } catch (e: Exception) {
            LoadResult.Error(
                throwable = e
            )
        }
    }

    override fun getRefreshKey(state: PagingState<Int, SongDataEntity>): Int? {
        return null
    }
}