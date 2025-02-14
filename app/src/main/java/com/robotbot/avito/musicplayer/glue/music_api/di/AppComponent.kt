package com.robotbot.avito.musicplayer.glue.music_api.di

import com.robotbot.avito.data.music.di.ApiModule
import com.robotbot.avito.data.music.di.DataModule
import com.robotbot.avito.data.music.di.MusicRepositoriesDataModule
import com.robotbot.avito.data.music.di.MusicSourcesModule
import com.robotbot.avito.music_api.di.MusicApiComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    MusicRepositoriesModule::class,
    DataModule::class
])
interface AppComponent {

    fun musicApiComponent(): MusicApiComponent
}