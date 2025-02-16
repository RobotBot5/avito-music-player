package com.robotbot.avito.music_local.presentation.music_local

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.robotbot.avito.music_local.domain.GetLocalMusicUseCase
import javax.inject.Inject

class MusicLocalViewModelFactory @Inject constructor(
    private val getLocalMusicUseCase: GetLocalMusicUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MusicLocalViewModel(getLocalMusicUseCase) as T
    }
}