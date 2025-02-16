package com.robotbot.avito.musicplayer.glue.music_api.mappers

import com.robotbot.avito.data.music.entities.LocalSongDataEntity
import com.robotbot.avito.data.music.entities.SongDataEntity
import com.robotbot.avito.muic_list_core.domain.entities.LoadingProgress
import com.robotbot.avito.muic_list_core.domain.entities.Song
import com.robotbot.avito.muic_list_core.domain.entities.SongToDisplay
import com.robotbot.avito.music_player.domain.entities.SongToPlay

object MusicMapper {

    fun mapDataEntityToDomain(songDataEntity: SongDataEntity): Song = Song(
        id = songDataEntity.id,
        title = songDataEntity.title,
        authorName = songDataEntity.authorName,
        songImageUrl = songDataEntity.albumDataEntity?.coverMediumUrl,
        previewUrl = songDataEntity.previewUrl
    )

    fun mapSongToLocalSongDataEntity(song: Song): LocalSongDataEntity = LocalSongDataEntity(
        id = song.id.toString(),
        title = song.title,
        authorName = song.authorName,
        songImageUrl = song.songImageUrl
    )

    fun mapLocalSongDataEntityToDisplaySong(localSongDataEntity: LocalSongDataEntity): SongToDisplay = SongToDisplay(
        id = localSongDataEntity.id,
        title = localSongDataEntity.title,
        authorName = localSongDataEntity.authorName,
        songImageUrl = localSongDataEntity.songImageUrl,
        loadingProgress = LoadingProgress.LOADED
    )

    fun mapDataEntityToSongToPlay(songDataEntity: SongDataEntity): SongToPlay = SongToPlay(
        id = songDataEntity.id.toString(),
        title = songDataEntity.title,
        authorName = songDataEntity.authorName,
        previewUrl = songDataEntity.previewUrl,
        songImageUrl = songDataEntity.albumDataEntity?.coverBigUrl,
        albumId = songDataEntity.albumDataEntity?.id
    )
}