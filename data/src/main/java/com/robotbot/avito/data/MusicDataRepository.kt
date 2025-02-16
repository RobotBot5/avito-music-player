package com.robotbot.avito.data

import androidx.paging.PagingData
import com.robotbot.avito.data.music.entities.LocalSongDataEntity
import com.robotbot.avito.data.music.entities.SongDataEntity
import kotlinx.coroutines.flow.Flow

interface MusicDataRepository {

    fun getMusicChart(): Flow<PagingData<SongDataEntity>>

    fun searchMusic(searchQuery: String): Flow<PagingData<SongDataEntity>>

    suspend fun saveSongIntoDb(localSongDataEntity: LocalSongDataEntity)

    fun getLocalSongIds(): Flow<Set<String>>

    suspend fun getSongById(id: String): SongDataEntity

    fun getLocalMusicWithPaging(searchQuery: String): Flow<PagingData<LocalSongDataEntity>>

    suspend fun getSongsByAlbumId(albumId: Long): List<SongDataEntity>

    fun getAllTracks(): Flow<List<LocalSongDataEntity>>

}