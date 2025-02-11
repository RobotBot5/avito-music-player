package com.robotbot.avito.data.music.sources

import com.robotbot.avito.data.music.entities.SongDataEntity
import com.robotbot.avito.data.music.sources.base.MusicApi
import com.robotbot.avito.data.music.sources.base.wrapRetrofitExceptions
import javax.inject.Inject

class RealRemoteDataSource @Inject constructor(
    private val musicApi: MusicApi
) : RemoteDataSource {

    override suspend fun getMusicChart(): List<SongDataEntity> = wrapRetrofitExceptions {
        musicApi.getMusicChart().toSongDataEntityList()
    }

    override suspend fun searchMusic(searchQuery: String): List<SongDataEntity> = wrapRetrofitExceptions {
        musicApi.searchMusic(searchQuery).toSongDataEntityList()
    }
}