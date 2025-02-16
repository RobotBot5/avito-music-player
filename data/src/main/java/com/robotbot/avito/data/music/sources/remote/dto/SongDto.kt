package com.robotbot.avito.data.music.sources.remote.dto

import com.google.gson.annotations.SerializedName
import com.robotbot.avito.common.ParseBackendResponseException
import com.robotbot.avito.data.music.entities.SongDataEntity

data class SongDto(
    @SerializedName("id") val id: Long? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("preview") val previewUrl: String? = null,
    @SerializedName("artist") val artist: ArtistDto? = null,
    @SerializedName("album") val albumDto: AlbumDto? = null,
    @SerializedName("error") override val error: ErrorResponseDto? = null
) : DtoWithError {

    fun toSongDataEntity(): SongDataEntity {
        return try {
            SongDataEntity(
                id = id!!,
                title = title!!,
                authorName = artist!!.name,
                albumDataEntity = albumDto?.toAlbumDataEntity(),
                previewUrl = previewUrl!!
            )
        } catch (e: Exception) {
            throw ParseBackendResponseException(e)
        }
    }
}