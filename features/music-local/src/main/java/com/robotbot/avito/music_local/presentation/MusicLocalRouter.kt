package com.robotbot.avito.music_local.presentation

import androidx.navigation.NavController

interface MusicLocalRouter {

    fun startMusic(trackId: String, navController: NavController)
}