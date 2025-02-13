package com.robotbot.avito.data.music.sources.base

import com.robotbot.avito.data.music.sources.dto.music.ChartResponseDto
import com.robotbot.avito.data.music.sources.dto.music.TrackListWrapperDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MusicApi {

    @GET("chart/0/tracks")
    suspend fun getMusicChart(
        @Query("limit") limit: Int? = null,
        @Query("index") index: Int? = null
    ): TrackListWrapperDto

    @GET("search")
    suspend fun searchMusic(
        @Query("q") searchQuery: String,
        @Query("limit") limit: Int? = null,
        @Query("index") index: Int? = null
    ): TrackListWrapperDto

}