package com.robotbot.avito.music_api.presentation.music_api

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robotbot.avito.music_api.domain.GetChartMusicListUseCase
import com.robotbot.avito.music_api.domain.SearchMusicUseCase
import com.robotbot.avito.music_api.domain.entities.Song
import com.robotbot.avito.music_api.presentation.MusicApiRouter
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class MusicApiViewModel(
    private val getChartMusicListUseCase: GetChartMusicListUseCase,
    private val searchMusicUseCase: SearchMusicUseCase,
    private val musicApiRouter: MusicApiRouter
) : ViewModel() {

    private val _state = MutableStateFlow<MusicApiState>(MusicApiState.Initial)
    val state = _state.asStateFlow()

    private var chartMusic: List<Song> = listOf()

    private val _searchQuery = MutableStateFlow("")

    init {
        viewModelScope.launch {
            _state.value = MusicApiState.Loading
            chartMusic = getChartMusicListUseCase()
            _state.value = MusicApiState.ChartMusic(chartMusic)
        }

        viewModelScope.launch {
            _searchQuery
                .filter { it.isNotEmpty() }
                .debounce(DEBOUNCE_SEARCH_IN_MILLIS)
                .collect {
                    _state.value = MusicApiState.SearchMusic(searchMusicUseCase(it))
                }
        }
    }

    fun setNewSearchQuery(searchQuery: String) {
        val trimmedSearchQuery = searchQuery.trim()
        if (trimmedSearchQuery.isEmpty()) {
            _state.value = MusicApiState.ChartMusic(chartMusic)
        } else {
            viewModelScope.launch {
                _searchQuery.value = trimmedSearchQuery

            }
        }
    }

    companion object {

        private const val DEBOUNCE_SEARCH_IN_MILLIS = 500L
    }
}