package com.robotbot.avito.music_api

import android.app.Notification
import android.util.Log
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.util.Util
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.cache.NoOpCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import androidx.media3.exoplayer.offline.Download
import androidx.media3.exoplayer.offline.DownloadManager
import androidx.media3.exoplayer.offline.DownloadNotificationHelper
import androidx.media3.exoplayer.offline.DownloadService
import androidx.media3.exoplayer.scheduler.PlatformScheduler
import androidx.media3.exoplayer.scheduler.Scheduler
import androidx.paging.LOG_TAG
import androidx.work.WorkManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.cancel
import java.io.File
import java.lang.Exception


@OptIn(UnstableApi::class)
class DownloadMusicService : DownloadService(
    FOREGROUND_NOTIFICATION_ID,
    DEFAULT_FOREGROUND_NOTIFICATION_UPDATE_INTERVAL,
    CHANNEL_ID,
    R.string.download_channel_name,
    R.string.title_music_api
) {

    override fun onCreate() {
        Log.d(LOG_TAG, "onCreate")
        super.onCreate()
    }

    override fun onDestroy() {
        Log.d(LOG_TAG, "onDestroy")
        super.onDestroy()
    }

    override fun getDownloadManager(): DownloadManager {
        val databaseProvider = StandaloneDatabaseProvider(this)
        val downloadDirectory = File(this.filesDir, DIRECTORY_NAME).apply {
            if (!exists()) mkdirs()
        }
        val downloadCache = SimpleCache(downloadDirectory, NoOpCacheEvictor(), databaseProvider)
        val dataSourceFactory = DefaultHttpDataSource.Factory()
        val slowDataSourceFactory = SlowDataSourceFactory(dataSourceFactory, delayMillis = 1000L)
        val downloadExecutor = Dispatchers.IO.asExecutor()

        val downloadManager = DownloadManager(
            this,
            databaseProvider,
            downloadCache,
            slowDataSourceFactory,
            downloadExecutor
        )

//        val downloadNotificationHelper: DownloadNotificationHelper =
//            DemoUtil.getDownloadNotificationHelper( /* context= */this)
//        downloadManager.addListener(
//            TerminalStateNotificationHelper(
//                this, downloadNotificationHelper, FOREGROUND_NOTIFICATION_ID + 1
//            )
//        )


        downloadManager.addListener(object : DownloadManager.Listener {
            override fun onDownloadChanged(
                downloadManager: DownloadManager,
                download: Download,
                finalException: Exception?
            ) {
                Log.d(LOG_TAG, "${downloadManager.currentDownloads.map { it.request.id }} ${download.state}")
                DownloadTracker.updateDownloads(downloadManager.currentDownloads.map { it.request.id }.toSet())
                if (download.state == Download.STATE_COMPLETED) {
                    val metaJsonString = String(download.request.data)
                    val workManager = WorkManager.getInstance(application)
                    workManager.enqueue(SaveSongIntoDbWorker.makeRequest(metaJsonString))
                }
            }
        })
        return downloadManager
    }

    override fun getScheduler(): Scheduler? {
        return if (Util.SDK_INT < 31) {
            PlatformScheduler(this, JOB_ID)
        } else {
            null
        }
    }

    override fun getForegroundNotification(
        downloads: MutableList<Download>,
        notMetRequirements: Int
    ): Notification {
        return DownloadNotificationHelper(this, CHANNEL_ID).buildProgressNotification(
            this,
            R.drawable.ic_download,
            null,
            null,
            downloads,
            notMetRequirements
        );
    }

    companion object {
        private const val FOREGROUND_NOTIFICATION_ID = 1
        private const val JOB_ID = 1001
        private const val CHANNEL_ID = "download_channel"
        private const val DIRECTORY_NAME = "downloads"

        private const val LOG_TAG = "DownloadMusicService"
    }
}