package com.robotbot.avito.music_api.domain.repositores

import androidx.paging.PagingData
import com.robotbot.avito.music_api.domain.entities.Song
import kotlinx.coroutines.flow.Flow

interface MusicRepository {

    suspend fun getMusicChart(): Flow<PagingData<Song>>

    suspend fun searchMusic(searchQuery: String): Flow<PagingData<Song>>
}