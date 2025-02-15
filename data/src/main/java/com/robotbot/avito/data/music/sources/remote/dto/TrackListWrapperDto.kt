package com.robotbot.avito.data.music.sources.remote.dto

import com.google.gson.annotations.SerializedName
import com.robotbot.avito.data.music.entities.SongDataEntity

data class TrackListWrapperDto(
    @SerializedName("data") var tracks: List<ChartSongDto>? = null,
    @SerializedName("error") override val error: ErrorResponseDto?
) : DtoWithError {

    fun toSongDataEntityList(): List<SongDataEntity> = tracks?.map { it.toSongDataEntity() } ?: listOf()
}