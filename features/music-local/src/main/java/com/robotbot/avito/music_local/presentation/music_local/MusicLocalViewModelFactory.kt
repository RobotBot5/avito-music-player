package com.robotbot.avito.music_local.presentation.music_local

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.robotbot.avito.music_local.domain.GetLocalMusicUseCase
import com.robotbot.avito.music_local.presentation.MusicLocalRouter
import javax.inject.Inject

class MusicLocalViewModelFactory @Inject constructor(
    private val getLocalMusicUseCase: GetLocalMusicUseCase,
    private val musicLocalRouter: MusicLocalRouter
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MusicLocalViewModel(
            getLocalMusicUseCase,
            musicLocalRouter
        ) as T
    }
}