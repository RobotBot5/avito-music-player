package com.robotbot.avito.data.music.sources.remote

import com.robotbot.avito.data.music.entities.SongDataEntity
import com.robotbot.avito.data.music.sources.RemoteDataSource
import com.robotbot.avito.data.music.sources.remote.base.MusicApi
import com.robotbot.avito.data.music.sources.remote.base.wrapRetrofitExceptions
import javax.inject.Inject

typealias MusicPageLoader = suspend (pageIndex: Int, pageSize: Int) -> List<SongDataEntity>

class RealRemoteDataSource @Inject constructor(
    private val musicApi: MusicApi
) : RemoteDataSource {

    override suspend fun getMusicChart(
        limit: Int?,
        index: Int?,
    ): List<SongDataEntity> = wrapRetrofitExceptions {
        musicApi.getMusicChart(limit, index)
    }.toSongDataEntityList()

    override suspend fun searchMusic(
        searchQuery: String,
        limit: Int?,
        index: Int?,
    ): List<SongDataEntity> = wrapRetrofitExceptions {
        musicApi.searchMusic(searchQuery, limit, index)
    }.toSongDataEntityList()

    override suspend fun getSongById(id: String): SongDataEntity = wrapRetrofitExceptions {
        musicApi.getSongById(id).toSongDataEntity()
    }

    override suspend fun getSongsByAlbumId(id: Long): List<SongDataEntity> = wrapRetrofitExceptions {
        musicApi.getAlbumById(id).toSongDataEntityList()
    }
}