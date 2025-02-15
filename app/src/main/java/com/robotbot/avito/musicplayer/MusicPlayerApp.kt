package com.robotbot.avito.musicplayer

import android.app.Application
import androidx.work.Configuration
import com.robotbot.avito.music_api.SaveSongIntoDbWorkerFactory
import com.robotbot.avito.music_api.di.MusicApiComponent
import com.robotbot.avito.music_api.di.MusicApiComponentProvider
import com.robotbot.avito.musicplayer.glue.music_api.di.DaggerAppComponent
import javax.inject.Inject

class MusicPlayerApp : Application(), MusicApiComponentProvider, Configuration.Provider {

    val component by lazy {
        DaggerAppComponent
            .factory()
            .create(this)
    }

    @Inject
    lateinit var saveSongIntoDbWorkerFactory: SaveSongIntoDbWorkerFactory

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(saveSongIntoDbWorkerFactory)
            .build()



    override fun provideMusicApiComponent(): MusicApiComponent = component.musicApiComponent()

}