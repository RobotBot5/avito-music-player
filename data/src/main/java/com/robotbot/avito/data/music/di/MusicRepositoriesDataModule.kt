package com.robotbot.avito.data.music.di

import com.robotbot.avito.data.MusicDataRepository
import com.robotbot.avito.data.music.RealMusicDataRepository
import dagger.Binds
import dagger.Module

@Module
interface MusicRepositoriesDataModule {

    @Binds
    fun bindMusicRepository(impl: RealMusicDataRepository): MusicDataRepository
}