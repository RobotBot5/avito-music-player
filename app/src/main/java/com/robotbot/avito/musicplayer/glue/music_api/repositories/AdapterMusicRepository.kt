package com.robotbot.avito.musicplayer.glue.music_api.repositories

import androidx.paging.PagingData
import androidx.paging.map
import com.robotbot.avito.data.MusicDataRepository
import com.robotbot.avito.music_api.domain.entities.Song
import com.robotbot.avito.music_api.domain.repositores.MusicRepository
import com.robotbot.avito.musicplayer.glue.music_api.mappers.MusicMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AdapterMusicRepository @Inject constructor(
    private val musicDataRepository: MusicDataRepository
) : MusicRepository {

    override fun getMusicChart(): Flow<PagingData<Song>> {
        return musicDataRepository.getMusicChart().map {
            it.map(MusicMapper::mapDataEntityToDomain)
        }
    }

    override fun searchMusic(searchQuery: String): Flow<PagingData<Song>> {
        return musicDataRepository.searchMusic(searchQuery)
            .map {
                it.map(MusicMapper::mapDataEntityToDomain)
            }
    }
}