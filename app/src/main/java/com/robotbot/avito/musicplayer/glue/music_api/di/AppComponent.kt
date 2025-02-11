package com.robotbot.avito.musicplayer.glue.music_api.di

import com.robotbot.avito.data.music.di.ApiModule
import com.robotbot.avito.data.music.di.MusicRepositoriesDataModule
import com.robotbot.avito.data.music.di.MusicSourcesModule
import com.robotbot.avito.music_api.di.MusicApiComponent
import dagger.Component

@Component(modules = [
    MusicRepositoriesModule::class,
    ApiModule::class,
    MusicRepositoriesDataModule::class,
    MusicSourcesModule::class
])
interface AppComponent {

    fun musicApiComponent(): MusicApiComponent
}