package com.robotbot.avito.data.music.di

import dagger.Module

@Module(includes = [
    ApiModule::class,
    MusicRepositoriesDataModule::class,
    MusicSourcesModule::class
])
interface DataModule