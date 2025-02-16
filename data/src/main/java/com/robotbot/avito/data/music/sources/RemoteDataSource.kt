package com.robotbot.avito.data.music.sources

import com.robotbot.avito.data.music.entities.SongDataEntity

interface RemoteDataSource {

    suspend fun getMusicChart(
        limit: Int? = null,
        index: Int? = null
    ): List<SongDataEntity>

    suspend fun searchMusic(
        searchQuery: String,
        limit: Int? = null,
        index: Int? = null
    ): List<SongDataEntity>

    suspend fun getSongById(
        id: String
    ): SongDataEntity
}