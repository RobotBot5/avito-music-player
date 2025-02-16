package com.robotbot.avito.music_api.domain.repositores

import androidx.paging.PagingData
import com.robotbot.avito.music_api.domain.entities.Song
import kotlinx.coroutines.flow.Flow

interface MusicRepository {

    fun getMusicChart(): Flow<PagingData<Song>>

    fun searchMusic(searchQuery: String): Flow<PagingData<Song>>

    suspend fun saveSongIntoDb(song: Song)

    fun getLocalMusicIds(): Flow<Set<String>>
}