package com.robotbot.avito.data.music

import com.robotbot.avito.data.MusicDataRepository
import com.robotbot.avito.data.music.di.MusicSourcesModule
import com.robotbot.avito.data.music.entities.SongDataEntity
import com.robotbot.avito.data.music.sources.RemoteDataSource
import javax.inject.Inject

class RealMusicDataRepository @Inject constructor(
    private val musicSource: RemoteDataSource
) : MusicDataRepository {
    override suspend fun getMusicChart(): List<SongDataEntity> =  musicSource.getMusicChart()

    override suspend fun searchMusic(searchQuery: String): List<SongDataEntity> = musicSource.searchMusic(searchQuery)
}