package com.robotbot.avito.music_api.domain

import com.robotbot.avito.music_api.domain.repositores.MusicRepository
import javax.inject.Inject

class GetSongByIdUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {

    suspend operator fun invoke(id: String) = musicRepository.getSongById(id)
}