package com.robotbot.avito.data.music.entities

data class SongDataEntity(
    val id: Long,
    val title: String,
    val authorName: String,
    val previewUrl: String,
    val albumDataEntity: AlbumDataEntity?
)