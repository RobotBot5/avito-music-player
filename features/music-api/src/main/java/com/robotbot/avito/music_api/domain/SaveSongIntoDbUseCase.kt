package com.robotbot.avito.music_api.domain

import com.robotbot.avito.muic_list_core.domain.entities.Song
import com.robotbot.avito.music_api.domain.repositores.MusicRepository
import javax.inject.Inject

class SaveSongIntoDbUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {

    suspend operator fun invoke(song: Song) {
        musicRepository.saveSongIntoDb(song)
    }
}