package com.robotbot.avito.musicplayer.glue.music_api.repositories

import androidx.paging.PagingData
import androidx.paging.map
import com.robotbot.avito.data.MusicDataRepository
import com.robotbot.avito.muic_list_core.domain.entities.SongToDisplay
import com.robotbot.avito.music_local.domain.repositories.LocalMusicRepository
import com.robotbot.avito.musicplayer.glue.music_api.mappers.MusicMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AdapterLocalMusicRepository @Inject constructor(
    private val musicDataRepository: MusicDataRepository
) : LocalMusicRepository {

    override fun getLocalMusic(searchQuery: String): Flow<PagingData<SongToDisplay>> {
        return musicDataRepository.getLocalMusic(searchQuery).map {
            it.map(MusicMapper::mapLocalSongDataEntityToDisplaySong)
        }
    }
}