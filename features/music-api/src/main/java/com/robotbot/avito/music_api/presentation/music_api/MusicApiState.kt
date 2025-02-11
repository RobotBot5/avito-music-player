package com.robotbot.avito.music_api.presentation.music_api

import com.robotbot.avito.music_api.domain.entities.Song

sealed interface MusicApiState {

    data object Initial : MusicApiState
    data object Loading : MusicApiState
    data class ChartMusic(val songs: List<Song>) : MusicApiState
    data class SearchMusic(val songs: List<Song>) : MusicApiState
    data class Error(val msg: String) : MusicApiState
}
