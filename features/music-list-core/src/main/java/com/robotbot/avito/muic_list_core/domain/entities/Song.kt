package com.robotbot.avito.muic_list_core.domain.entities

data class Song(
    val id: Long,
    val title: String,
    val authorName: String,
    val previewUrl: String,
    val songImageUrl: String?
) {

    fun toSongToDisplay(loadingProgress: LoadingProgress): SongToDisplay = SongToDisplay(
        id = id.toString(),
        title = title,
        authorName = authorName,
        songImageUrl = songImageUrl,
        loadingProgress = loadingProgress
    )
}