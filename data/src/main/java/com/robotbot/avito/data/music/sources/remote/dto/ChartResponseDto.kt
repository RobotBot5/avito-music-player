package com.robotbot.avito.data.music.sources.remote.dto

import com.google.gson.annotations.SerializedName
import com.robotbot.avito.data.music.entities.SongDataEntity

data class ChartResponseDto(
    @SerializedName("tracks") val trackListWrapperDto: TrackListWrapperDto? = null,
    @SerializedName("error") override val error: ErrorResponseDto?
) : DtoWithError {

    fun toSongDataEntityList(): List<SongDataEntity> = trackListWrapperDto?.toSongDataEntityList() ?: listOf()
}
