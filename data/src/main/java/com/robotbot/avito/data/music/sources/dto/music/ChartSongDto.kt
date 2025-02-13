package com.robotbot.avito.data.music.sources.dto.music

import com.google.gson.annotations.SerializedName
import com.robotbot.avito.data.music.entities.SongDataEntity

data class ChartSongDto(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("artist") val artist: ArtistDto,
    @SerializedName("album") val albumDto: AlbumDto
) {

    fun toSongDataEntity(): SongDataEntity = SongDataEntity(
        id = id,
        title = title,
        authorName = artist.name,
        songImageUrl = albumDto.coverMediumUrl
    )
}