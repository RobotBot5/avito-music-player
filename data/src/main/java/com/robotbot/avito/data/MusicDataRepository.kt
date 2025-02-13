package com.robotbot.avito.data

import androidx.paging.PagingData
import com.robotbot.avito.data.music.entities.SongDataEntity
import kotlinx.coroutines.flow.Flow

interface MusicDataRepository {

    suspend fun getMusicChart(): Flow<PagingData<SongDataEntity>>

    suspend fun searchMusic(searchQuery: String): Flow<PagingData<SongDataEntity>>

}