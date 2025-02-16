package com.robotbot.avito.musicplayer.glue.music_api.repositories

import com.robotbot.avito.data.MusicDataRepository
import com.robotbot.avito.music_player.domain.entities.LocalSongToPlay
import com.robotbot.avito.music_player.domain.entities.SongToPlay
import com.robotbot.avito.music_player.domain.repositories.MusicToPlayRepository
import com.robotbot.avito.musicplayer.glue.music_api.mappers.MusicMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AdapterMusicToPlayRepository @Inject constructor(
    private val musicDataRepository: MusicDataRepository
) : MusicToPlayRepository {

    override suspend fun getSongToPlayById(id: String): SongToPlay =
        MusicMapper.mapDataEntityToSongToPlay(musicDataRepository.getSongById(id))

    override fun getLocalTracks(): Flow<List<LocalSongToPlay>> = musicDataRepository.getAllTracks().map {
        it.map(MusicMapper::mapLocalDataEntityToPlay)
    }


}