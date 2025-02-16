package com.robotbot.avito.music_player.domain.repositories

import com.robotbot.avito.music_player.domain.entities.SongToPlay

interface AlbumRepository {

    suspend fun getTracksByAlbumId(albumId: Long): List<SongToPlay>
}