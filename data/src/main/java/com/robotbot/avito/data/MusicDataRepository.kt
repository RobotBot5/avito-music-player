package com.robotbot.avito.data

import com.robotbot.avito.data.music.entities.SongDataEntity

interface MusicDataRepository {

    suspend fun getMusicChart(): List<SongDataEntity>

    suspend fun searchMusic(searchQuery: String): List<SongDataEntity>

}