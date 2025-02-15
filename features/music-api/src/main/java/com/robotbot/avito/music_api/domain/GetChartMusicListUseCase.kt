package com.robotbot.avito.music_api.domain

import com.robotbot.avito.music_api.di.MusicApiFeatureScope
import com.robotbot.avito.music_api.domain.repositores.MusicRepository
import javax.inject.Inject

@MusicApiFeatureScope
internal class GetChartMusicListUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {

    operator fun invoke() = musicRepository.getMusicChart()
}