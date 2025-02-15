package com.robotbot.avito.musicplayer.glue.music_api.di

import android.app.Application
import com.robotbot.avito.data.music.di.DataModule
import com.robotbot.avito.music_api.di.MusicApiComponent
import com.robotbot.avito.musicplayer.MusicPlayerApp
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    MusicRepositoriesModule::class,
    DataModule::class
])
interface AppComponent {

    fun musicApiComponent(): MusicApiComponent

    fun inject(app: MusicPlayerApp)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }
}