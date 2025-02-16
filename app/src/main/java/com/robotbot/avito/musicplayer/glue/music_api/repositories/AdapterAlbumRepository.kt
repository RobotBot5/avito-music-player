package com.robotbot.avito.musicplayer.glue.music_api.repositories

import com.robotbot.avito.data.MusicDataRepository
import com.robotbot.avito.music_player.domain.repositories.AlbumRepository
import com.robotbot.avito.musicplayer.glue.music_api.mappers.MusicMapper
import javax.inject.Inject

class AdapterAlbumRepository @Inject constructor(
    private val musicDataRepository: MusicDataRepository
) : AlbumRepository {

    override suspend fun getTracksByAlbumId(albumId: Long) =
        musicDataRepository.getSongsByAlbumId(albumId).map(MusicMapper::mapDataEntityToSongToPlay)

}