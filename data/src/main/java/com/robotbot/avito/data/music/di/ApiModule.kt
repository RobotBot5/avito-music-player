package com.robotbot.avito.data.music.di

import com.robotbot.avito.data.music.sources.base.ApiFactory
import com.robotbot.avito.data.music.sources.base.MusicApi
import dagger.Module
import dagger.Provides

@Module
class ApiModule {

    @Provides
    fun provideMusicApi(): MusicApi = ApiFactory.musicApi
}