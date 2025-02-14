package com.robotbot.avito.data.music.di

import com.robotbot.avito.data.MusicDataRepository
import com.robotbot.avito.data.music.RealMusicDataRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface MusicRepositoriesDataModule {

    @Binds
    @Singleton
    fun bindMusicRepository(impl: RealMusicDataRepository): MusicDataRepository
}