package com.robotbot.avito.musicplayer.glue.music_api

import androidx.navigation.NavController
import com.robotbot.avito.music_api.presentation.MusicApiRouter
import com.robotbot.avito.music_player.PlayerFragment
import com.robotbot.avito.musicplayer.R
import javax.inject.Inject

class AdapterMusicApiRouter @Inject constructor(
) : MusicApiRouter {

    override fun startMusic(trackId: String, navController: NavController) {
        navController.navigate(
            R.id.testFragment,
            PlayerFragment.newBundle(trackId)
        )
    }
}