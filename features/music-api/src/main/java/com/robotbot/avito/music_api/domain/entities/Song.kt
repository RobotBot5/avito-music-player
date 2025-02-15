package com.robotbot.avito.music_api.domain.entities

data class Song(
    val id: Long,
    val title: String,
    val authorName: String,
    val previewUrl: String,
    val songImageUrl: String?
)