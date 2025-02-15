package com.robotbot.avito.data.music.di

import com.robotbot.avito.data.music.sources.LocalDataSource
import com.robotbot.avito.data.music.sources.remote.RealRemoteDataSource
import com.robotbot.avito.data.music.sources.RemoteDataSource
import com.robotbot.avito.data.music.sources.local.RealLocalDataSource
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface MusicSourcesModule {

    @Binds
    @Singleton
    fun bindMusicRemoteSource(
        impl: RealRemoteDataSource
    ): RemoteDataSource

    @Binds
    @Singleton
    fun bindMusicLocalSource(
        impl: RealLocalDataSource
    ): LocalDataSource
}