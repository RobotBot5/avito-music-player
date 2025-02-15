package com.robotbot.avito.music_api

import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource

@UnstableApi
class SlowDataSourceFactory(
    private val delegateFactory: DataSource.Factory,
    private val delayMillis: Long = 50L
) : DataSource.Factory {
    override fun createDataSource(): DataSource {
        // Оборачиваем созданный DataSource в SlowDataSource
        val delegate = delegateFactory.createDataSource()
        return SlowDataSource(delegate, delayMillis)
    }
}