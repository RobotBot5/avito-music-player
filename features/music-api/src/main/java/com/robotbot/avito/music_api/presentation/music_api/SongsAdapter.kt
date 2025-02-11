package com.robotbot.avito.music_api.presentation.music_api

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.robotbot.avito.music_api.databinding.ItemSongBinding
import com.robotbot.avito.music_api.domain.entities.Song
import javax.inject.Inject

class SongsAdapter @Inject constructor() : ListAdapter<Song, SongViewHolder>(SongDiffCallback) {

    val onDownloadClickListener: ((Song) -> Unit)? = null
    val onSongClickListener: ((Song) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding = ItemSongBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SongViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = getItem(position)
        with(holder.binding) {
            Glide.with(holder.itemView)
                .load(song.songImageUrl)
                .into(songImageView)
            titleTextView.text = song.title
            authorTextView.text = song.authorName
            downloadImageView.setOnClickListener {
                onDownloadClickListener?.invoke(song)
            }
            root.setOnClickListener {
                onSongClickListener?.invoke(song)
            }
        }
    }
}