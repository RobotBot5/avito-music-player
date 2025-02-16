package com.robotbot.avito.music_player.domain

import com.robotbot.avito.music_player.domain.repositories.MusicToPlayRepository
import javax.inject.Inject

class GetLocalTracksUseCase @Inject constructor(
    private val musicToPlayRepository: MusicToPlayRepository
) {

    operator fun invoke() = musicToPlayRepository.getLocalTracks()
}