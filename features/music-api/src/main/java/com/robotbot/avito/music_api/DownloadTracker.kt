package com.robotbot.avito.music_api

import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@OptIn(UnstableApi::class)
object DownloadTracker {

    private val _downloadsFlow = MutableStateFlow<Set<String>>(emptySet())
    val downloadsFlow: StateFlow<Set<String>> = _downloadsFlow.asStateFlow()

    fun updateDownloads(downloads: Set<String>) {
        _downloadsFlow.value = downloads
    }
}