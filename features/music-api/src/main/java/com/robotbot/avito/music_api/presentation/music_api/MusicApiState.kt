package com.robotbot.avito.music_api.presentation.music_api

import androidx.paging.PagingData
import com.robotbot.avito.music_api.domain.entities.SongToDisplay

data class MusicApiState(
    val displayState: MusicApiDisplayState = MusicApiDisplayState.Initial,
    val musicList: PagingData<SongToDisplay> = PagingData.empty()
)

sealed interface MusicApiDisplayState {

    data object Initial : MusicApiDisplayState
    data object Loading : MusicApiDisplayState
    data object ChartMusic : MusicApiDisplayState
    data object SearchMusic : MusicApiDisplayState
    data class Error(val msg: String) : MusicApiDisplayState
}
