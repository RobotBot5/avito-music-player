package com.robotbot.avito.music_player

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.robotbot.avito.music_player.domain.GetAlbumTracksByTrackIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlayerViewModel(
    private val getAlbumTracksByTrackIdUseCase: GetAlbumTracksByTrackIdUseCase
) : ViewModel() {

    private val _musicList = MutableStateFlow<Pair<Long, List<MediaItem>>>(-1L to listOf())
    val musicList = _musicList.asStateFlow()

    private val _state = MutableStateFlow(PlayerState())
    val state = _state.asStateFlow()

    private var prevAlbumId: Long = INIT_PREV_ALBUM_ID

    fun setAlbumInPlayer(trackId: String) {
        viewModelScope.launch {
            val (albumId, musicList) = getAlbumTracksByTrackIdUseCase(trackId)
            if (albumId == prevAlbumId) return@launch
            prevAlbumId = albumId
            _musicList.value = albumId to musicList
                .map { song ->
                    val metaData = MediaMetadata.Builder()
                        .setTitle(song.title)
                        .setArtworkUri(Uri.parse(song.songImageUrl))
                        .setAlbumTitle(song.albumTitle)
                        .setArtist(song.authorName).build()

                    MediaItem.Builder()
                        .setUri(song.previewUrl)
                        .setMediaId(song.id)
                        .setMediaMetadata(metaData)
                        .build()
                }
        }
    }

    fun setState(
        title: String,
        author: String,
        album: String,
        imageUrl: String
    ) {
        _state.value = PlayerState(title, author, album, imageUrl)
    }

    companion object {
        private const val INIT_PREV_ALBUM_ID = -1L
    }
}