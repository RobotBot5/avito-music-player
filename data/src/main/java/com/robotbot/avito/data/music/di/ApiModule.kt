package com.robotbot.avito.data.music.di

import com.robotbot.avito.data.music.sources.remote.base.ApiFactory
import com.robotbot.avito.data.music.sources.remote.base.MusicApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideMusicApi(): MusicApi = ApiFactory.musicApi
}