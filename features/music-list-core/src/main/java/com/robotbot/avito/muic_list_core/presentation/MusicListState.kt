package com.robotbot.avito.muic_list_core.presentation

import androidx.paging.PagingData
import com.robotbot.avito.muic_list_core.domain.entities.SongToDisplay

data class MusicListState(
    val displayState: MusicListDisplayState = MusicListDisplayState.Initial,
    val musicList: PagingData<SongToDisplay> = PagingData.empty()
)

sealed interface MusicListDisplayState {

    data object Initial : MusicListDisplayState
    data object Loading : MusicListDisplayState
    data object ChartMusic : MusicListDisplayState
    data object SearchMusic : MusicListDisplayState
    data class Error(val msg: String) : MusicListDisplayState
}