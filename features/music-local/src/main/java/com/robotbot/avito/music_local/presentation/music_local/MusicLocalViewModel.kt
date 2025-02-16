package com.robotbot.avito.music_local.presentation.music_local

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.paging.cachedIn
import com.robotbot.avito.muic_list_core.presentation.BaseMusicListViewModel
import com.robotbot.avito.muic_list_core.presentation.MusicListDisplayState
import com.robotbot.avito.muic_list_core.presentation.MusicListState
import com.robotbot.avito.music_local.domain.GetLocalMusicUseCase
import com.robotbot.avito.music_local.presentation.MusicLocalRouter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class MusicLocalViewModel @Inject constructor(
    private val getLocalMusicUseCase: GetLocalMusicUseCase,
    private val musicLocalRouter: MusicLocalRouter
) : BaseMusicListViewModel() {

    private val _musicList = MutableStateFlow(MusicListState())
    override val musicList = _musicList.asStateFlow()

    init {
        observeSearchQuery()
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun observeSearchQuery() {
        searchQuery
            .debounce(DEBOUNCE_SEARCH_IN_MILLIS)
            .flatMapLatest { query ->
                getLocalMusicUseCase(query)
            }.cachedIn(viewModelScope)
            .onEach { songs ->
                _musicList.update {
                    it.copy(musicList = songs)
                }
            }.launchIn(viewModelScope)
    }

    fun playMusic(songId: String, navController: NavController) {
        musicLocalRouter.startMusic(songId, navController)
    }

    companion object {

        private const val DEBOUNCE_SEARCH_IN_MILLIS = 500L
    }
}