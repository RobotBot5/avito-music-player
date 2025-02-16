package com.robotbot.avito.data.music

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.robotbot.avito.data.MusicDataRepository
import com.robotbot.avito.data.music.entities.LocalSongDataEntity
import com.robotbot.avito.data.music.entities.SongDataEntity
import com.robotbot.avito.data.music.sources.LocalDataSource
import com.robotbot.avito.data.music.sources.remote.MusicPageLoader
import com.robotbot.avito.data.music.sources.PagingDataSource
import com.robotbot.avito.data.music.sources.RemoteDataSource
import com.robotbot.avito.data.music.sources.local.toLocalSongDataEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RealMusicDataRepository @Inject constructor(
    private val remoteMusicSource: RemoteDataSource,
    private val localMusicSource: LocalDataSource
) : MusicDataRepository {

    override fun getMusicChart(): Flow<PagingData<SongDataEntity>> {
        val loader: MusicPageLoader = { pageIndex, pageSize ->
            getChartMusic(pageIndex, pageSize)
        }
        return getDefaultPager(loader).flow
    }

    override fun searchMusic(searchQuery: String): Flow<PagingData<SongDataEntity>> {
        val loader: MusicPageLoader = { pageIndex, pageSize ->
            getMusicBySearch(pageIndex, pageSize, searchQuery)
        }
        return getDefaultPager(loader).flow
    }

    override suspend fun saveSongIntoDb(localSongDataEntity: LocalSongDataEntity) {
        localMusicSource.saveSong(localSongDataEntity)
    }

    override fun getLocalSongIds(): Flow<Set<String>> = localMusicSource.getLocalMusicIds()

    override suspend fun getSongById(id: String): SongDataEntity = remoteMusicSource.getSongById(id)

    override fun getLocalMusic(searchQuery: String): Flow<PagingData<LocalSongDataEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                initialLoadSize = PAGE_SIZE,
                prefetchDistance = PAGE_SIZE / 2,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { localMusicSource.getLocalMusic(searchQuery) }
        ).flow
            .map {
                it.map { songDbModel ->
                    songDbModel.toLocalSongDataEntity()
                }
            }
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
        return remoteMusicSource.getMusicChart(pageSize, offset)
    }

    private suspend fun getMusicBySearch(
        pageIndex: Int,
        pageSize: Int,
        searchQuery: String
    ): List<SongDataEntity> {
        val offset = pageIndex * pageSize
        return remoteMusicSource.searchMusic(searchQuery, pageSize, offset)
    }

    private companion object {
        const val PAGE_SIZE = 15
    }
}