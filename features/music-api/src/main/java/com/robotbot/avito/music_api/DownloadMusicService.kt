package com.robotbot.avito.music_api

import android.app.Notification
import android.content.Context
import android.util.Log
import androidx.annotation.OptIn
import androidx.media3.common.util.NotificationUtil
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
import androidx.work.WorkManager
import com.robotbot.avito.service_core.CacheHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import java.io.File


@OptIn(UnstableApi::class)
class DownloadMusicService : DownloadService(
    FOREGROUND_NOTIFICATION_ID,
    DEFAULT_FOREGROUND_NOTIFICATION_UPDATE_INTERVAL,
    CHANNEL_ID,
    R.string.download_channel_name,
    R.string.title_music_api
) {

    override fun onDestroy() {
        CacheHolder.releaseCache()
        super.onDestroy()
    }

    override fun getDownloadManager(): DownloadManager {
        val databaseProvider = StandaloneDatabaseProvider(this)
//        val downloadDirectory = File(this.filesDir, DIRECTORY_NAME).apply {
//            if (!exists()) mkdirs()
//        }
//        val downloadCache = SimpleCache(downloadDirectory, NoOpCacheEvictor(), databaseProvider)
        val downloadCache = CacheHolder.getInstance(this)
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

        val downloadNotificationHelper = DownloadNotificationHelper(this, CHANNEL_ID)
        downloadManager.addListener(
            TerminalStateNotificationHelper(
                this, downloadNotificationHelper, FOREGROUND_NOTIFICATION_ID + 1
            )
        )

        downloadManager.addListener(object : DownloadManager.Listener {
            override fun onDownloadChanged(
                downloadManager: DownloadManager,
                download: Download,
                finalException: Exception?
            ) {
                Log.d(
                    LOG_TAG,
                    "${downloadManager.currentDownloads.map { it.request.id }} ${download.state}"
                )
                DownloadTracker.updateDownloads(downloadManager.currentDownloads.map { it.request.id }
                    .toSet())
                if (download.state == Download.STATE_COMPLETED) {
                    val metaJsonString = String(download.request.data)
                    val workManager = WorkManager.getInstance(application)
                    workManager.enqueue(SaveSongIntoDbWorker.makeRequest(metaJsonString))
                }
            }
        })
        downloadManager.addListener(
            TerminalStateNotificationHelper(
                this, downloadNotificationHelper, FOREGROUND_NOTIFICATION_ID + 1
            )
        )
        return downloadManager
    }

    private class TerminalStateNotificationHelper(
        context: Context,
        private val notificationHelper: DownloadNotificationHelper,
        private var nextNotificationId: Int
    ) : DownloadManager.Listener {

        private val context: Context = context.applicationContext

        override fun onDownloadChanged(
            downloadManager: DownloadManager,
            download: Download,
            finalException: Exception?
        ) {
            val notification = if (download.state == Download.STATE_COMPLETED) {
                notificationHelper.buildDownloadCompletedNotification(
                    context,
                    com.robotbot.avito.muic_list_core.R.drawable.ic_search,
                    null,
                    Util.fromUtf8Bytes(download.request.data)
                )
            } else if (download.state == Download.STATE_FAILED) {
                notificationHelper.buildDownloadFailedNotification(
                    context,
                    com.robotbot.avito.muic_list_core.R.drawable.ic_search,
                    null,
                    Util.fromUtf8Bytes(download.request.data)
                )
            } else {
                return
            }
            NotificationUtil.setNotification(context, nextNotificationId++, notification)
        }
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
            com.robotbot.avito.muic_list_core.R.drawable.ic_download,
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