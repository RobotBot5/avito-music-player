package com.robotbot.avito.data.music.sources

import com.robotbot.avito.data.music.entities.SongDataEntity

interface RemoteDataSource {

    suspend fun getMusicChart(): List<SongDataEntity>

    suspend fun searchMusic(searchQuery: String): List<SongDataEntity>
}