package com.robotbot.avito.data.music.sources.dto.music

import com.google.gson.annotations.SerializedName

data class AlbumDto(
    @SerializedName("cover_small") val coverSmallUrl: String? = null
)
