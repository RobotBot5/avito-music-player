package com.robotbot.avito.muic_list_core.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.robotbot.avito.muic_list_core.domain.entities.SongToDisplay

object SongDiffCallback : DiffUtil.ItemCallback<SongToDisplay>() {

    override fun areItemsTheSame(oldItem: SongToDisplay, newItem: SongToDisplay): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SongToDisplay, newItem: SongToDisplay): Boolean {
        return oldItem == newItem
    }
}