package com.robotbot.avito.music_api.domain.entities

data class SongToDisplay(
    val id: Long,
    val title: String,
    val authorName: String,
    val previewUrl: String,
    val songImageUrl: String?,
    val loadingProgress: LoadingProgress
)
