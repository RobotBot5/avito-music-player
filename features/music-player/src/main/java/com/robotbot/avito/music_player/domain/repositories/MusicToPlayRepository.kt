package com.robotbot.avito.music_player.domain.repositories

import com.robotbot.avito.music_player.domain.entities.LocalSongToPlay
import com.robotbot.avito.music_player.domain.entities.SongToPlay
import kotlinx.coroutines.flow.Flow

interface MusicToPlayRepository {

    suspend fun getSongToPlayById(id: String): SongToPlay

    fun getLocalTracks(): Flow<List<LocalSongToPlay>>
}