package com.robotbot.avito.music_api.domain.repositores

import com.robotbot.avito.music_api.domain.entities.Song
import kotlinx.coroutines.flow.Flow

interface MusicRepository {

    suspend fun getMusicChart(): List<Song>

    suspend fun searchMusic(searchQuery: String): List<Song>
}