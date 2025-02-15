package com.robotbot.avito.music_api.presentation.music_api

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.robotbot.avito.music_api.domain.GetChartMusicListUseCase
import com.robotbot.avito.music_api.domain.SearchMusicUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

@OptIn(FlowPreview::class)
@ExperimentalCoroutinesApi
internal class MusicApiViewModel(
    private val getChartMusicListUseCase: GetChartMusicListUseCase,
    private val searchMusicUseCase: SearchMusicUseCase,
//    private val musicApiRouter: MusicApiRouter
) : ViewModel() {

    private val _state = MutableStateFlow(MusicApiState())
    val state = _state.asStateFlow()

    private val _searchQuery = MutableStateFlow("")

    init {
        _searchQuery
            .debounce(DEBOUNCE_SEARCH_IN_MILLIS)
            .flatMapLatest { query ->
                if (query.isBlank()) {
                    _state.update {
                        it.copy(displayState = MusicApiDisplayState.ChartMusic)
                    }
                    getChartMusicListUseCase()
                } else {
                    _state.update {
                        it.copy(displayState = MusicApiDisplayState.SearchMusic)
                    }
                    searchMusicUseCase(query)
                }
            }.cachedIn(viewModelScope)
            .onEach { musicList ->
                _state.update {
                    it.copy(musicList = musicList)
                }
            }
            .launchIn(viewModelScope)
    }

    fun setNewSearchQuery(searchQuery: String) {
        _searchQuery.value = searchQuery.trim()
    }

    companion object {

        private const val DEBOUNCE_SEARCH_IN_MILLIS = 500L
    }
}