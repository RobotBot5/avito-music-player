package com.robotbot.avito.musicplayer.glue.music_api.di

import com.robotbot.avito.music_api.domain.repositores.MusicRepository
import com.robotbot.avito.musicplayer.glue.music_api.repositories.AdapterMusicRepository
import dagger.Binds
import dagger.Module

@Module
interface MusicRepositoriesModule {

    @Binds
    fun bindsMusicRepository(impl: AdapterMusicRepository): MusicRepository
}