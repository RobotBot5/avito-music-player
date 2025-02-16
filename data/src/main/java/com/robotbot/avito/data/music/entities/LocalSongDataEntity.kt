package com.robotbot.avito.data.music.entities

data class LocalSongDataEntity(
    val id: String,
    val title: String,
    val authorName: String,
    val previewUrl: String,
    val songImageUrl: String?
)