package com.robotbot.avito.music_player

import android.content.Intent
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.cache.NoOpCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.session.DefaultMediaNotificationProvider
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.robotbot.avito.service_core.CacheHolder
import java.io.File

class PlaybackService : MediaSessionService() {
    private var mediaSession: MediaSession? = null

    @OptIn(UnstableApi::class)
    override fun onCreate() {
        super.onCreate()
//        val databaseProvider = StandaloneDatabaseProvider(this)
//        val downloadDirectory = File(this.filesDir, DIRECTORY_NAME).apply {
//            if (!exists()) mkdirs()
//        }
//        val downloadCache = SimpleCache(downloadDirectory, NoOpCacheEvictor(), databaseProvider)
        val cacheDataSourceFactory = CacheDataSource.Factory()
            .setCache(CacheHolder.getInstance(this))
            .setUpstreamDataSourceFactory(DefaultHttpDataSource.Factory())
            .setCacheWriteDataSinkFactory(null)
        val player = ExoPlayer.Builder(this)
            .setMediaSourceFactory(
                DefaultMediaSourceFactory(this).setDataSourceFactory(cacheDataSourceFactory)
            )
            .build()
        mediaSession = MediaSession.Builder(this, player).build()
    }

    @OptIn(UnstableApi::class)
    override fun onDestroy() {
        mediaSession?.run {
            player.release()
            release()
            mediaSession = null
        }
        CacheHolder.releaseCache()
        super.onDestroy()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return mediaSession
    }

    @OptIn(UnstableApi::class)
    override fun onTaskRemoved(rootIntent: Intent?) {
        pauseAllPlayersAndStopSelf()
    }

    companion object {

        private const val DIRECTORY_NAME = "downloads"
    }
}