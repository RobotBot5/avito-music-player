package com.robotbot.avito.music_player.domain.repositories

import com.robotbot.avito.music_player.domain.entities.SongToPlay

interface MusicToPlayRepository {

    suspend fun getSongToPlayById(id: String): SongToPlay
}