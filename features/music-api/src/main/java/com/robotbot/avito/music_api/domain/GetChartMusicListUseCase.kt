package com.robotbot.avito.music_api.domain

import com.robotbot.avito.music_api.domain.repositores.MusicRepository
import javax.inject.Inject

class GetChartMusicListUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {

    suspend operator fun invoke() = musicRepository.getMusicChart()
}