package com.robotbot.avito.data.music.sources

import androidx.paging.PagingSource
import com.robotbot.avito.data.music.entities.LocalSongDataEntity
import com.robotbot.avito.data.music.entities.SongDataEntity
import com.robotbot.avito.data.music.sources.local.model.SongDbModel
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun saveSong(localSongDataEntity: LocalSongDataEntity)

    fun getLocalMusicIds(): Flow<Set<String>>

    fun getLocalMusic(searchQuery: String): PagingSource<Int, SongDbModel>
}