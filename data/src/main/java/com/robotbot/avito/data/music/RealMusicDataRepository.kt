package com.robotbot.avito.data.music

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.robotbot.avito.data.MusicDataRepository
import com.robotbot.avito.data.music.entities.SongDataEntity
import com.robotbot.avito.data.music.sources.MusicPageLoader
import com.robotbot.avito.data.music.sources.PagingDataSource
import com.robotbot.avito.data.music.sources.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RealMusicDataRepository @Inject constructor(
    private val musicSource: RemoteDataSource
) : MusicDataRepository {

    override suspend fun getMusicChart(): Flow<PagingData<SongDataEntity>> {
        val loader: MusicPageLoader = { pageIndex, pageSize ->
            getChartMusic(pageIndex, pageSize)
        }
        return getDefaultPager(loader).flow
    }

    override suspend fun searchMusic(searchQuery: String): Flow<PagingData<SongDataEntity>> {
        val loader: MusicPageLoader = { pageIndex, pageSize ->
            getMusicBySearch(pageIndex, pageSize, searchQuery)
        }
        return getDefaultPager(loader).flow
    }

    private fun getDefaultPager(loader: MusicPageLoader): Pager<Int, SongDataEntity> = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { PagingDataSource(loader, PAGE_SIZE) }
    )

    private suspend fun getChartMusic(pageIndex: Int, pageSize: Int): List<SongDataEntity> {
        val offset = pageIndex * pageSize
        return musicSource.getMusicChart(pageSize, offset)
    }

    private suspend fun getMusicBySearch(pageIndex: Int, pageSize: Int, searchQuery: String): List<SongDataEntity> {
        val offset = pageIndex * pageSize
        return musicSource.searchMusic(searchQuery, pageSize, offset)
    }

    private companion object {
        const val PAGE_SIZE = 10
    }
}