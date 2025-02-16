package com.robotbot.avito.music_local.domain.repositories

import androidx.paging.PagingData
import com.robotbot.avito.muic_list_core.domain.entities.SongToDisplay
import kotlinx.coroutines.flow.Flow

interface LocalMusicRepository {

    fun getLocalMusic(searchQuery: String): Flow<PagingData<SongToDisplay>>
}