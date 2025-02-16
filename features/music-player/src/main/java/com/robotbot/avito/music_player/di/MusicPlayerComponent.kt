package com.robotbot.avito.music_player.di

import com.robotbot.avito.music_player.PlayerFragment
import dagger.Subcomponent

@Subcomponent
@MusicPlayerScope
interface MusicPlayerComponent {

    fun inject(fragment: PlayerFragment)
}