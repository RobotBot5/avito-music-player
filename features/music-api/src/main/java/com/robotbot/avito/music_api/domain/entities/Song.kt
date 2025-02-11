package com.robotbot.avito.music_api.domain.entities

data class Song(
    val id: Int,
    val title: String,
    val authorName: String,
    val songImageUrl: String
)