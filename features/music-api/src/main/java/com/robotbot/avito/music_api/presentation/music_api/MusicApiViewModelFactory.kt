package com.robotbot.avito.music_api.presentation.music_api

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.robotbot.avito.music_api.domain.GetChartMusicListUseCase
import com.robotbot.avito.music_api.domain.GetLocalMusicIdsUseCase
import com.robotbot.avito.music_api.domain.GetSongByIdUseCase
import com.robotbot.avito.music_api.domain.SearchMusicUseCase
import com.robotbot.avito.music_api.presentation.MusicApiRouter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

internal class MusicApiViewModelFactory @Inject constructor(
    private val application: Application,
    private val getChartMusicListUseCase: GetChartMusicListUseCase,
    private val searchMusicUseCase: SearchMusicUseCase,
    private val getLocalMusicIdsUseCase: GetLocalMusicIdsUseCase,
    private val getSongByIdUseCase: GetSongByIdUseCase,
    private val musicApiRouter: MusicApiRouter
) : ViewModelProvider.Factory {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MusicApiViewModel(
            application,
            getChartMusicListUseCase,
            searchMusicUseCase,
            getLocalMusicIdsUseCase,
            getSongByIdUseCase,
            musicApiRouter
        ) as T
    }
}