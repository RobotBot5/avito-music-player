package com.robotbot.avito.music_api.presentation

import androidx.navigation.NavController

interface MusicApiRouter {

    fun startMusic(trackId: String, navController: NavController)
}