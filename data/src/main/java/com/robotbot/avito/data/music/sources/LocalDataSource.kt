package com.robotbot.avito.data.music.sources

import com.robotbot.avito.data.music.entities.LocalSongDataEntity
import com.robotbot.avito.data.music.entities.SongDataEntity

interface LocalDataSource {

    suspend fun saveSong(localSongDataEntity: LocalSongDataEntity)

    suspend fun getMusicChart(limit: Int, offset: Int): List<SongDataEntity>
}