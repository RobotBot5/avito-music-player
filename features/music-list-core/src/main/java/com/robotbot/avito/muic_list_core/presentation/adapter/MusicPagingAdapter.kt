package com.robotbot.avito.muic_list_core.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import com.bumptech.glide.Glide
import com.robotbot.avito.muic_list_core.R
import com.robotbot.avito.muic_list_core.databinding.ItemSongBinding
import com.robotbot.avito.muic_list_core.domain.entities.LoadingProgress
import com.robotbot.avito.muic_list_core.domain.entities.SongToDisplay

typealias OnDownloadClickListener = (SongToDisplay) -> Unit

typealias OnDeleteClickListener = (SongToDisplay) -> Unit

class MusicPagingAdapter : PagingDataAdapter<SongToDisplay, SongViewHolder>(SongDiffCallback) {

    var onDownloadClickListener: OnDownloadClickListener? = null

    var onDeleteClickListener: OnDeleteClickListener? = null

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
            when (song.loadingProgress) {
                LoadingProgress.NOT_LOADING -> {
                    downloadImageView.visibility = android.view.View.VISIBLE
                    progressBarDownloading.visibility = android.view.View.INVISIBLE
                    deleteImageView.visibility = android.view.View.INVISIBLE
                }
                LoadingProgress.LOADING -> {
                    downloadImageView.visibility = android.view.View.INVISIBLE
                    progressBarDownloading.visibility = android.view.View.VISIBLE
                    deleteImageView.visibility = android.view.View.INVISIBLE
                }
                LoadingProgress.LOADED -> {
                    downloadImageView.visibility = android.view.View.INVISIBLE
                    progressBarDownloading.visibility = android.view.View.INVISIBLE
                    deleteImageView.visibility = android.view.View.VISIBLE
                }
            }
            downloadImageView.setOnClickListener {
                onDownloadClickListener?.invoke(song)
            }
            deleteImageView.setOnClickListener {
                onDeleteClickListener?.invoke(song)
            }
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