package com.robotbot.avito.music_api.presentation.music_api

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.robotbot.avito.music_api.domain.GetChartMusicListUseCase
import com.robotbot.avito.music_api.domain.SearchMusicUseCase
import com.robotbot.avito.music_api.presentation.MusicApiRouter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

internal class MusicApiViewModelFactory @Inject constructor(
    private val getChartMusicListUseCase: GetChartMusicListUseCase,
    private val searchMusicUseCase: SearchMusicUseCase,
//    private val musicApiRouter: MusicApiRouter
) : ViewModelProvider.Factory {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MusicApiViewModel(getChartMusicListUseCase, searchMusicUseCase) as T
    }
}