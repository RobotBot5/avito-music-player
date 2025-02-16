package com.robotbot.avito.muic_list_core.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseMusicListViewModel(
) : ViewModel() {

    abstract val musicList: StateFlow<MusicListState>

    protected val searchQuery = MutableStateFlow("")

    fun setNewSearchQuery(searchQuery: String) {
        this.searchQuery.value = searchQuery.trim()
    }
}