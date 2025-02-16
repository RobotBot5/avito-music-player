package com.robotbot.avito.music_player.domain.entities

data class LocalSongToPlay(
    val id: String,
    val title: String,
    val authorName: String,
    val previewUrl: String,
    val songImageUrl: String?,
)