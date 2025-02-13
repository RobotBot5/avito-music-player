package com.robotbot.avito.data

import androidx.paging.PagingData
import com.robotbot.avito.data.music.entities.SongDataEntity
import kotlinx.coroutines.flow.Flow

interface MusicDataRepository {

    fun getMusicChart(): Flow<PagingData<SongDataEntity>>

    fun searchMusic(searchQuery: String): Flow<PagingData<SongDataEntity>>



}