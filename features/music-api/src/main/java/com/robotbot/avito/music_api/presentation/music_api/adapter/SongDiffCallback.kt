package com.robotbot.avito.music_api.presentation.music_api.adapter

import androidx.recyclerview.widget.DiffUtil
import com.robotbot.avito.music_api.domain.entities.Song

object SongDiffCallback : DiffUtil.ItemCallback<Song>() {

    override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
        return oldItem == newItem
    }
}