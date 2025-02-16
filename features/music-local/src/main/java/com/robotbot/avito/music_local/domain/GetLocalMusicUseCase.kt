package com.robotbot.avito.music_local.domain

import com.robotbot.avito.music_local.domain.repositories.LocalMusicRepository
import javax.inject.Inject

class GetLocalMusicUseCase @Inject constructor(
    private val localMusicRepository: LocalMusicRepository
) {

    operator fun invoke(searchQuery: String) = localMusicRepository.getLocalMusic(searchQuery)
}