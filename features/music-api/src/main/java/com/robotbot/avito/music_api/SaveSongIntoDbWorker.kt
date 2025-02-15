package com.robotbot.avito.music_api

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.google.gson.Gson
import com.robotbot.avito.music_api.domain.SaveSongIntoDbUseCase
import com.robotbot.avito.music_api.domain.entities.Song

class SaveSongIntoDbWorker(
    context: Context,
    workerParameters: WorkerParameters,
    private val saveSongIntoDbUseCase: SaveSongIntoDbUseCase
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val json = inputData.getString(SONG_DATA_JSON_KEY) ?: return Result.failure()
        val song = Gson().fromJson(json, Song::class.java)

        return try {
            saveSongIntoDbUseCase(song)
            Result.success()
        } catch (e: Exception) {
            Log.e(LOG_TAG, "$e")
            Result.failure()
        }
    }

    companion object {

        private const val SONG_DATA_JSON_KEY = "songJson"
        private const val LOG_TAG = "SaveSongIntoDbWorker"

        fun makeRequest(songDataJson: String): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<SaveSongIntoDbWorker>()
                .setInputData(workDataOf(SONG_DATA_JSON_KEY to songDataJson))
                .build()
        }
    }
}