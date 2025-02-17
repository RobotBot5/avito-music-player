package com.robotbot.avito.musicplayer.glue.music_api.di

import android.app.Application
import com.robotbot.avito.data.music.di.DataModule
import com.robotbot.avito.music_api.di.MusicApiComponent
import com.robotbot.avito.music_local.di.MusicLocalComponent
import com.robotbot.avito.music_player.di.MusicPlayerComponent
import com.robotbot.avito.musicplayer.MainActivity
import com.robotbot.avito.musicplayer.MusicPlayerApp
import dagger.BindsInstance
import dagger.Component
import dagger.Subcomponent
import javax.inject.Singleton

@Singleton
@Component(modules = [
    MusicRepositoriesModule::class,
    RoutersModule::class,
    DataModule::class
])
interface AppComponent {

    fun musicApiComponent(): MusicApiComponent

    fun musicLocalComponent(): MusicLocalComponent

    fun musicPlayerComponent(): MusicPlayerComponent

    fun inject(app: MusicPlayerApp)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }
}