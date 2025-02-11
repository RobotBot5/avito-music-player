package com.robotbot.avito.music_api.di

import com.robotbot.avito.music_api.presentation.music_api.MusicApiFragment
import dagger.Component

@Component
interface MusicApiComponent {

    fun inject(fragment: MusicApiFragment)
}