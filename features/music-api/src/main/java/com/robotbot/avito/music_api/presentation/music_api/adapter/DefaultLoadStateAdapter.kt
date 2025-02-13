package com.robotbot.avito.music_api.presentation.music_api.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.robotbot.avito.common.BackendException
import com.robotbot.avito.common.ConnectionException
import com.robotbot.avito.common.ParseBackendResponseException
import com.robotbot.avito.music_api.R
import com.robotbot.avito.music_api.databinding.PartDefaultLoadStateBinding

typealias TryAgainAction = () -> Unit

class DefaultLoadStateAdapter(
    private val tryAgainAction: TryAgainAction
) : LoadStateAdapter<DefaultLoadStateAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): Holder {
        val binding = PartDefaultLoadStateBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return Holder(binding, tryAgainAction)
    }

    override fun onBindViewHolder(holder: Holder, loadState: LoadState) {
        holder.bind(loadState)
    }

    class Holder(
        private val binding: PartDefaultLoadStateBinding,
        private val tryAgainAction: TryAgainAction
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.tryAgainButton.setOnClickListener { tryAgainAction() }
        }

        fun bind(loadState: LoadState) = with(binding) {
            messageTextView.isVisible = loadState is LoadState.Error
            tryAgainButton.isVisible = loadState is LoadState.Error
            progressBar.isVisible = loadState is LoadState.Loading
            if (loadState is LoadState.Error) {
                messageTextView.text = when (loadState.error) {
                    is BackendException -> binding.root.context.getString(R.string.error_msg_server)
                    is ConnectionException -> binding.root.context.getString(R.string.error_msg_connection)
                    is ParseBackendResponseException -> binding.root.context.getString(R.string.error_msg_server_parsing)
                    else -> binding.root.context.getString(R.string.error_msg_unknown)
                }
            }
        }
    }

}