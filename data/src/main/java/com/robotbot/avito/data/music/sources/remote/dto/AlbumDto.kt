package com.robotbot.avito.data.music.sources.remote.dto

import com.google.gson.annotations.SerializedName
import com.robotbot.avito.data.music.entities.AlbumDataEntity

data class AlbumDto(
    @SerializedName("id") val id: Long,
    @SerializedName("cover_medium") val coverMediumUrl: String? = null,
    @SerializedName("cover_big") val coverBigUrl: String? = null
) {

    fun toAlbumDataEntity(): AlbumDataEntity = AlbumDataEntity(
        id = id,
        coverMediumUrl = coverMediumUrl,
        coverBigUrl = coverBigUrl
    )
}
