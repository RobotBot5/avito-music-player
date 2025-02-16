package com.robotbot.avito.musicplayer.glue.music_api.di

import com.robotbot.avito.music_api.presentation.MusicApiRouter
import com.robotbot.avito.musicplayer.glue.music_api.AdapterMusicApiRouter
import dagger.Binds
import dagger.Module

@Module
interface RoutersModule {

    @Binds
    fun bindMusicApiRouter(impl: AdapterMusicApiRouter): MusicApiRouter
}