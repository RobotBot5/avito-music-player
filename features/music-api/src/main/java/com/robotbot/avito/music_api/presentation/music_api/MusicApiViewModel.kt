package com.robotbot.avito.music_api.presentation.music_api

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.offline.DownloadRequest
import androidx.media3.exoplayer.offline.DownloadService
import androidx.navigation.NavController
import androidx.paging.cachedIn
import androidx.paging.map
import com.google.gson.Gson
import com.robotbot.avito.common.combineTriple
import com.robotbot.avito.muic_list_core.domain.entities.LoadingProgress
import com.robotbot.avito.muic_list_core.domain.entities.SongToDisplay
import com.robotbot.avito.muic_list_core.presentation.BaseMusicListViewModel
import com.robotbot.avito.muic_list_core.presentation.MusicListDisplayState
import com.robotbot.avito.muic_list_core.presentation.MusicListState
import com.robotbot.avito.music_api.DownloadMusicService
import com.robotbot.avito.music_api.DownloadTracker
import com.robotbot.avito.music_api.domain.GetChartMusicListUseCase
import com.robotbot.avito.music_api.domain.GetLocalMusicIdsUseCase
import com.robotbot.avito.music_api.domain.GetSongByIdUseCase
import com.robotbot.avito.music_api.domain.SearchMusicUseCase
import com.robotbot.avito.music_api.presentation.MusicApiRouter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
@androidx.annotation.OptIn(UnstableApi::class)
@ExperimentalCoroutinesApi
class MusicApiViewModel(
    private val application: Application,
    private val getChartMusicListUseCase: GetChartMusicListUseCase,
    private val searchMusicUseCase: SearchMusicUseCase,
    private val getLocalMusicIdsUseCase: GetLocalMusicIdsUseCase,
    private val getSongByIdUseCase: GetSongByIdUseCase,
    private val musicApiRouter: MusicApiRouter
) : BaseMusicListViewModel() {

    private val _musicList = MutableStateFlow(MusicListState())
    override val musicList = _musicList.asStateFlow()

    init {
        observeSearchQuery()

        DownloadTracker.downloadsFlow
            .onEach { Log.d(LOG_TAG, "Downloads: $it") }
            .launchIn(viewModelScope)
    }

    private fun observeSearchQuery() {
        searchQuery
            .debounce(DEBOUNCE_SEARCH_IN_MILLIS)
            .flatMapLatest { query ->
                if (query.isBlank()) {
                    _musicList.update {
                        it.copy(displayState = MusicListDisplayState.ChartMusic)
                    }
                    getChartMusicListUseCase()
                } else {
                    _musicList.update {
                        it.copy(displayState = MusicListDisplayState.SearchMusic)
                    }
                    searchMusicUseCase(query)
                }
            }.cachedIn(viewModelScope)
            .combineTriple(
                DownloadTracker.downloadsFlow,
                getLocalMusicIdsUseCase()
            ) { songs, downloading, downloaded ->
                songs.map { song ->
                    if (downloading.contains(song.id.toString())) {
                        song.toSongToDisplay(LoadingProgress.LOADING)
                    } else if (downloaded.contains(song.id.toString())) {
                        song.toSongToDisplay(LoadingProgress.LOADED)
                    } else {
                        song.toSongToDisplay(LoadingProgress.NOT_LOADING)
                    }
                }
            }.onEach { musicList ->
                _musicList.update {
                    it.copy(musicList = musicList)
                }
            }
            .launchIn(viewModelScope)
    }

    fun downloadSong(songId: String) {
        viewModelScope.launch {
            val song = try {
                 getSongByIdUseCase(songId)
            } catch (e: Exception) {
                return@launch
            }

            val downloadRequest = DownloadRequest.Builder(
                song.id.toString(),
                Uri.parse(song.previewUrl)
            ).setData(Gson().toJson(song).toByteArray()).build()

            DownloadService.start(
                application,
                DownloadMusicService::class.java
            )

            DownloadService.sendAddDownload(
                application,
                DownloadMusicService::class.java,
                downloadRequest,
                false
            )
        }
    }

    fun startPlayMusic(trackId: String, navController: NavController) {
        musicApiRouter.startMusic(trackId, navController)
    }

    companion object {

        private const val DEBOUNCE_SEARCH_IN_MILLIS = 500L

        private const val LOG_TAG = "MusicApiViewModel"
    }

}