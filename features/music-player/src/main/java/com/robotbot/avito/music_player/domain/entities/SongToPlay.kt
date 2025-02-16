package com.robotbot.avito.music_player.domain.entities

data class SongToPlay(
    val id: String,
    val title: String,
    val authorName: String,
    val previewUrl: String,
    val songImageUrl: String?,
    val albumId: Long?,
    val albumTitle: String?
)
