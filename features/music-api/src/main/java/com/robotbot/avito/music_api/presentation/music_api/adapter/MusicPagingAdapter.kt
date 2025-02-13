package com.robotbot.avito.music_api.presentation.music_api.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import com.bumptech.glide.Glide
import com.robotbot.avito.music_api.R
import com.robotbot.avito.music_api.databinding.ItemSongBinding
import com.robotbot.avito.music_api.domain.entities.Song

class MusicPagingAdapter : PagingDataAdapter<Song, SongViewHolder>(SongDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding = ItemSongBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SongViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = getItem(position) ?: return
        with (holder.binding) {
            titleTextView.text = song.title
            authorTextView.text = song.authorName
            loadSongPhoto(songImageView, song.songImageUrl)
        }
    }

    private fun loadSongPhoto(imageView: ImageView, url: String?) {
        val context = imageView.context
        if (!url.isNullOrBlank()) {
            Glide.with(context)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.placeholder_song)
                .error(R.drawable.placeholder_song)
                .into(imageView)
        } else {
            Glide.with(context)
                .load(R.drawable.placeholder_song)
                .into(imageView)
        }
    }
}