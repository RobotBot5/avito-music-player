package com.robotbot.avito.music_api.domain

import com.robotbot.avito.music_api.domain.repositores.MusicRepository
import javax.inject.Inject

class SearchMusicUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {

    suspend operator fun invoke(searchQuery: String) = musicRepository.searchMusic(searchQuery)
}