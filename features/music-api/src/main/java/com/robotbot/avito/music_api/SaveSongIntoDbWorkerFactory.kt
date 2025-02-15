package com.robotbot.avito.music_api

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.robotbot.avito.music_api.domain.SaveSongIntoDbUseCase
import javax.inject.Inject

class SaveSongIntoDbWorkerFactory @Inject constructor(
    private val saveSongIntoDbUseCase: SaveSongIntoDbUseCase
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker {
        return SaveSongIntoDbWorker(
            appContext,
            workerParameters,
            saveSongIntoDbUseCase
        )
    }
}