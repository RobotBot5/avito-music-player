package com.robotbot.avito.data.music.di

import android.app.Application
import com.robotbot.avito.data.music.sources.local.base.LocalSongsDao
import com.robotbot.avito.data.music.sources.local.base.SongDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DaoModule {

    @Provides
    @Singleton
    fun provideDownloadTracksDao(application: Application): LocalSongsDao =
        SongDatabase.getInstance(application).localSongsDao()
}