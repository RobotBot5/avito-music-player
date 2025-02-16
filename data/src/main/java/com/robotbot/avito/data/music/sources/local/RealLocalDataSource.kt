package com.robotbot.avito.data.music.sources.local

import com.robotbot.avito.data.music.entities.LocalSongDataEntity
import com.robotbot.avito.data.music.sources.LocalDataSource
import com.robotbot.avito.data.music.sources.local.base.LocalSongsDao
import com.robotbot.avito.data.music.sources.local.base.wrapSQLiteException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RealLocalDataSource @Inject constructor(
    private val localSongsDao: LocalSongsDao
) : LocalDataSource {

    override suspend fun saveSong(localSongDataEntity: LocalSongDataEntity) {
        wrapSQLiteException {
            localSongsDao.saveSongs(localSongDataEntity.toSongDbModel())
        }
    }

    override fun getLocalMusicIds(): Flow<Set<String>> {
        return localSongsDao.getDownloadedTrackIds()
            .catch { e ->
                wrapSQLiteException { throw e }
            }.map { it.toSet() }
    }
}