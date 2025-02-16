package com.robotbot.avito.muic_list_core.domain.entities

data class SongToDisplay(
    val id: String,
    val title: String,
    val authorName: String,
    val songImageUrl: String?,
    val loadingProgress: LoadingProgress
)