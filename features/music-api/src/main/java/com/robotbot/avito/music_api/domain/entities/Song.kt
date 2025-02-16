package com.robotbot.avito.music_api.domain.entities

data class Song(
    val id: Long,
    val title: String,
    val authorName: String,
    val previewUrl: String,
    val songImageUrl: String?
) {

    fun toSongToDisplay(loadingProgress: LoadingProgress): SongToDisplay = SongToDisplay(
        id = id,
        title = title,
        authorName = authorName,
        previewUrl = previewUrl,
        songImageUrl = songImageUrl,
        loadingProgress = loadingProgress
    )
}