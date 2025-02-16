package com.robotbot.avito.muic_list_core.domain.entities

data class SongToDisplay(
    val id: Long,
    val title: String,
    val authorName: String,
    val songImageUrl: String?,
    val loadingProgress: LoadingProgress
)