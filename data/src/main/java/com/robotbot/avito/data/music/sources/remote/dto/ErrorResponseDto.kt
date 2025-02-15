package com.robotbot.avito.data.music.sources.remote.dto

import com.google.gson.annotations.SerializedName

data class ErrorResponseDto(
    @SerializedName("message") val message: String,
    @SerializedName("code") val code: Int
)