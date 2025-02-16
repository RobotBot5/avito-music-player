package com.robotbot.avito.music_api.presentation.music_api.adapter

import androidx.recyclerview.widget.DiffUtil
import com.robotbot.avito.music_api.domain.entities.SongToDisplay

object SongDiffCallback : DiffUtil.ItemCallback<SongToDisplay>() {

    override fun areItemsTheSame(oldItem: SongToDisplay, newItem: SongToDisplay): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SongToDisplay, newItem: SongToDisplay): Boolean {
        return oldItem == newItem
    }
}