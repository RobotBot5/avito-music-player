package com.robotbot.avito.data.music.sources.remote.dto

import com.google.gson.annotations.SerializedName

data class AlbumDto(
    @SerializedName("cover_medium") val coverMediumUrl: String? = null
)
