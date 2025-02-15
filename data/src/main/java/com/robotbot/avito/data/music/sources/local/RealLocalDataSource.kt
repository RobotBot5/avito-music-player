package com.robotbot.avito.data.music.sources.local

import com.robotbot.avito.data.music.entities.LocalSongDataEntity
import com.robotbot.avito.data.music.entities.SongDataEntity
import com.robotbot.avito.data.music.sources.LocalDataSource
import com.robotbot.avito.data.music.sources.local.base.LocalSongsDao
import com.robotbot.avito.data.music.sources.local.base.wrapSQLiteException
import javax.inject.Inject

class RealLocalDataSource @Inject constructor(
    private val localSongsDao: LocalSongsDao
) : LocalDataSource {

    override suspend fun saveSong(localSongDataEntity: LocalSongDataEntity) {
        wrapSQLiteException {
            localSongsDao.saveSongs(localSongDataEntity.toSongDbModel())
        }
    }

    override suspend fun getMusicChart(limit: Int, offset: Int): List<SongDataEntity> {
        TODO("Not yet implemented")
    }
}