package com.robotbot.avito.data.music.sources.remote.dto

import com.google.gson.annotations.SerializedName

data class ArtistDto(
    @SerializedName("name") val name: String
)