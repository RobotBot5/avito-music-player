package com.robotbot.avito.musicplayer.glue.music_api

import androidx.navigation.NavController
import com.robotbot.avito.music_local.presentation.MusicLocalRouter
import com.robotbot.avito.music_player.PlayerFragment
import com.robotbot.avito.musicplayer.R
import javax.inject.Inject

class AdapterMusicLocalRouter @Inject constructor() : MusicLocalRouter {

    override fun startMusic(trackId: String, navController: NavController) {
        navController.navigate(
            R.id.playerFragment,
            PlayerFragment.newBundleFromLocal(trackId)
        )
    }
}