package com.robotbot.avito.data.music.sources.local

import com.robotbot.avito.data.music.entities.LocalSongDataEntity
import com.robotbot.avito.data.music.sources.local.model.SongDbModel

fun LocalSongDataEntity.toSongDbModel(): SongDbModel = SongDbModel(
    id = id,
    title = title,
    authorName = authorName,
    songImageUrl = songImageUrl,
    previewUrl = previewUrl
)

fun SongDbModel.toLocalSongDataEntity(): LocalSongDataEntity = LocalSongDataEntity(
    id = id,
    title = title,
    authorName = authorName,
    songImageUrl = songImageUrl,
    previewUrl = previewUrl
)