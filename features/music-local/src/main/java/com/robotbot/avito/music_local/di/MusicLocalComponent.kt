package com.robotbot.avito.music_local.di

import com.robotbot.avito.music_local.presentation.music_local.MusicLocalFragment
import dagger.Subcomponent

@Subcomponent
@MusicLocalFeatureScope
interface MusicLocalComponent {

    fun inject(fragment: MusicLocalFragment)
}