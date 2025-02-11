package com.robotbot.avito.musicplayer.glue.music_api.repositories

import com.robotbot.avito.data.MusicDataRepository
import com.robotbot.avito.music_api.domain.entities.Song
import com.robotbot.avito.music_api.domain.repositores.MusicRepository
import com.robotbot.avito.musicplayer.glue.music_api.mappers.MusicMapper
import javax.inject.Inject

class AdapterMusicRepository @Inject constructor(
    private val musicDataRepository: MusicDataRepository
) : MusicRepository {

    override suspend fun getMusicChart(): List<Song> {
        return musicDataRepository.getMusicChart().map(MusicMapper::mapDataEntityToDomain)
    }

    override suspend fun searchMusic(searchQuery: String): List<Song> {
        return musicDataRepository.searchMusic(searchQuery).map(MusicMapper::mapDataEntityToDomain)
    }
}