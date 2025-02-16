package com.robotbot.avito.music_player.domain

import com.robotbot.avito.music_player.domain.entities.SongToPlay
import com.robotbot.avito.music_player.domain.repositories.AlbumRepository
import com.robotbot.avito.music_player.domain.repositories.MusicToPlayRepository
import javax.inject.Inject

class GetAlbumTracksByTrackIdUseCase @Inject constructor(
    private val musicToPlayRepository: MusicToPlayRepository,
    private val albumRepository: AlbumRepository
) {

    suspend operator fun invoke(trackId: String): Pair<Long, List<SongToPlay>> {
        val track = musicToPlayRepository.getSongToPlayById(trackId)

        val albumId = track.albumId ?: return 0L to listOf()

        return albumId to albumRepository.getTracksByAlbumId(albumId)
    }
}