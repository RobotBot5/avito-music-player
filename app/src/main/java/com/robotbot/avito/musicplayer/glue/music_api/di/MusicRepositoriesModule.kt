package com.robotbot.avito.musicplayer.glue.music_api.di

import com.robotbot.avito.music_api.domain.repositores.MusicRepository
import com.robotbot.avito.music_local.domain.repositories.LocalMusicRepository
import com.robotbot.avito.musicplayer.glue.music_api.repositories.AdapterLocalMusicRepository
import com.robotbot.avito.musicplayer.glue.music_api.repositories.AdapterMusicRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface MusicRepositoriesModule {

    @Binds
    @Singleton
    fun bindsMusicRepository(impl: AdapterMusicRepository): MusicRepository

    @Binds
    @Singleton
    fun bindsLocalMusicRepository(impl: AdapterLocalMusicRepository): LocalMusicRepository
}