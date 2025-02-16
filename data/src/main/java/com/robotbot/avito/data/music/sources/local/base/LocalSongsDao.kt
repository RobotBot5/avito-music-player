package com.robotbot.avito.data.music.sources.local.base

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.robotbot.avito.data.music.sources.local.model.SongDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalSongsDao {

    @Insert
    suspend fun saveSongs(songDbModel: SongDbModel)

    @Query("SELECT id FROM downloaded_tracks")
    fun getDownloadedTrackIds(): Flow<List<String>>
}