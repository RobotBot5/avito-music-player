package com.robotbot.avito.data.music.sources.local.base

import androidx.room.Dao
import androidx.room.Insert
import com.robotbot.avito.data.music.sources.local.model.SongDbModel

@Dao
interface LocalSongsDao {

    @Insert
    suspend fun saveSongs(songDbModel: SongDbModel)
}