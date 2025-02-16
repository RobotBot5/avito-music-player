package com.robotbot.avito.music_player

import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaMetadata
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import androidx.media3.ui.PlayerView
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import com.robotbot.avito.music_player.di.MusicPlayerComponentProvider
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlayerFragment : Fragment() {

    private lateinit var playerView: PlayerView
    private lateinit var controllerFuture: ListenableFuture<MediaController>

    private lateinit var trackId: String

    private val component by lazy {
        (requireActivity().application as MusicPlayerComponentProvider).provideMusicPlayerComponent()
    }

    @Inject
    lateinit var viewModelFactory: PlayerViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[PlayerViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
        viewModel.setAlbumInPlayer(trackId)
    }

    private fun parseArgs() {
        trackId = requireArguments().getString(TRACK_ID_KEY)
            ?: throw RuntimeException("Param track id is absent")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return PlayerView(requireContext())
            .also { playerView = it }
    }

    override fun onStart() {
        super.onStart()

        val sessionToken = SessionToken(
            requireContext(),
            ComponentName(requireContext(), PlaybackService::class.java)
        )
        controllerFuture = MediaController.Builder(requireContext(), sessionToken).buildAsync()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.musicList.collectLatest { (albumId, mediaList) ->
                controllerFuture.addListener(
                    {
                        val mediaController = controllerFuture.get()
                        playerView.player = mediaController
                        if (mediaController.playlistMetadata.title == albumId.toString()) return@addListener
                        mediaController.playlistMetadata = MediaMetadata.Builder().setTitle(albumId.toString()).build()
                        mediaController.setMediaItems(mediaList)
                    },
                    MoreExecutors.directExecutor()
                )
            }
        }
    }

    override fun onStop() {
        super.onStop()
        MediaController.releaseFuture(controllerFuture)
    }

    companion object {

        private const val TRACK_ID_KEY = "trackId"

        fun newBundle(trackId: String): Bundle = Bundle().apply {
            putString(TRACK_ID_KEY, trackId)
        }
    }
}