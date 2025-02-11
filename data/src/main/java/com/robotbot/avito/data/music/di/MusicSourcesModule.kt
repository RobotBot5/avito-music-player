package com.robotbot.avito.data.music.di

import com.robotbot.avito.data.music.sources.RealRemoteDataSource
import com.robotbot.avito.data.music.sources.RemoteDataSource
import dagger.Binds
import dagger.Module

@Module
interface MusicSourcesModule {

    @Binds
    fun bindMusicRemoteSource(
        impl: RealRemoteDataSource
    ): RemoteDataSource
}