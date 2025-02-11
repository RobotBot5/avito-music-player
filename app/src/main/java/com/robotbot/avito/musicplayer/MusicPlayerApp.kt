package com.robotbot.avito.musicplayer

import android.app.Application
import com.robotbot.avito.music_api.di.MusicApiComponent
import com.robotbot.avito.music_api.di.MusicApiComponentProvider
import com.robotbot.avito.musicplayer.glue.music_api.di.DaggerAppComponent

class MusicPlayerApp : Application(), MusicApiComponentProvider {

    val component by lazy {
        DaggerAppComponent.create()
    }

    override fun provideMusicApiComponent(): MusicApiComponent = component.musicApiComponent()

}