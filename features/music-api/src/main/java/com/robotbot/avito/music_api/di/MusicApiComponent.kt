package com.robotbot.avito.music_api.di

import com.robotbot.avito.music_api.presentation.music_api.MusicApiFragment
import dagger.Subcomponent

@Subcomponent
interface MusicApiComponent {

    fun inject(fragment: MusicApiFragment)
}