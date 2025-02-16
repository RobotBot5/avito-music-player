package com.robotbot.avito.data.music.sources.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.robotbot.avito.data.music.entities.LocalSongDataEntity

@Entity(tableName = "downloaded_tracks")
data class SongDbModel(
    @PrimaryKey
    val id: String,
    val title: String,
    val authorName: String,
    val previewUrl: String,
    val songImageUrl: String? = null
)