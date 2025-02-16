package com.robotbot.avito.service_core

import android.content.Context
import androidx.media3.common.util.UnstableApi
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.cache.NoOpCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import java.io.File

@UnstableApi
object CacheHolder {

    @Volatile
    private var instance: SimpleCache? = null

    fun getInstance(context: Context): SimpleCache {
        return instance ?: synchronized(this) {
            instance ?: createCache(context).also { instance = it }
        }
    }

    private fun createCache(context: Context): SimpleCache {
        val downloadDirectory = File(context.filesDir, "downloads").apply {
            if (!exists()) mkdirs()
        }
        val databaseProvider = StandaloneDatabaseProvider(context)
        return SimpleCache(downloadDirectory, NoOpCacheEvictor(), databaseProvider)
    }

    fun releaseCache() {
        instance?.release()
        instance = null
    }
}