package com.robotbot.avito.musicplayer.glue.music_api.mappers

import com.robotbot.avito.data.music.entities.LocalSongDataEntity
import com.robotbot.avito.data.music.entities.SongDataEntity
import com.robotbot.avito.music_api.domain.entities.Song

object MusicMapper {

    fun mapDataEntityToDomain(songDataEntity: SongDataEntity): Song = Song(
        id = songDataEntity.id,
        title = songDataEntity.title,
        authorName = songDataEntity.authorName,
        songImageUrl = songDataEntity.songImageUrl,
        previewUrl = songDataEntity.previewUrl
    )

    fun mapSongToLocalSongDataEntity(song: Song): LocalSongDataEntity = LocalSongDataEntity(
        id = song.id.toString(),
        title = song.title,
        authorName = song.authorName,
        songImageUrl = song.songImageUrl
    )
}