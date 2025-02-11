package com.robotbot.avito.music_api.domain.repositores

import com.robotbot.avito.music_api.domain.entities.Song
import kotlinx.coroutines.flow.Flow

interface MusicRepository {

    fun getMusicChart(): Flow<Song>

    fun searchMusic(searchQuery: String): Flow<Song>
}