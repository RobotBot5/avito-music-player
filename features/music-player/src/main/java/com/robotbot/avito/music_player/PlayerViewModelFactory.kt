package com.robotbot.avito.music_player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.robotbot.avito.music_player.domain.GetAlbumTracksByTrackIdUseCase
import javax.inject.Inject

class PlayerViewModelFactory @Inject constructor(
    private val getAlbumTracksByTrackIdUseCase: GetAlbumTracksByTrackIdUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlayerViewModel(
            getAlbumTracksByTrackIdUseCase
        ) as T
    }
}