package com.robotbot.avito.music_api.domain

import com.robotbot.avito.music_api.domain.repositores.MusicRepository
import javax.inject.Inject

class GetLocalMusicIdsUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {

    operator fun invoke() = musicRepository.getLocalMusicIds()
}