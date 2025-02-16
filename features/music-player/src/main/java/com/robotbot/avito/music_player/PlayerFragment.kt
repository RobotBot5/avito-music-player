package com.robotbot.avito.music_player

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.bumptech.glide.Glide
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import com.robotbot.avito.music_player.databinding.FragmentPlayerBinding
import com.robotbot.avito.music_player.di.MusicPlayerComponentProvider
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlayerFragment : Fragment() {

    private var _binding: FragmentPlayerBinding? = null
    private val binding: FragmentPlayerBinding
        get() = _binding ?: throw RuntimeException("FragmentPlayerBinding == null")

    private lateinit var controllerFuture: ListenableFuture<MediaController>

    private lateinit var trackId: String

    private lateinit var source: String

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
        if (source == REMOTE_SOURCE) {
            viewModel.setAlbumInPlayer(trackId)
        } else if (source == LOCAL_SOURCE) {
            viewModel.setLocalInPlayer()
        }
    }

    private fun parseArgs() {
        val bundle = requireArguments()
        trackId = bundle.getString(TRACK_ID_KEY)
            ?: throw RuntimeException("Param track id is absent")
        val source = bundle.getString(SOURCE_KEY) ?: throw RuntimeException("Param source is absent")
        if (source != LOCAL_SOURCE && source != REMOTE_SOURCE) {
            throw RuntimeException("Unknown source")
        }
        this.source = source
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    @OptIn(UnstableApi::class)
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
                        binding.playerView.player = mediaController
                        if (mediaController.playlistMetadata.title == albumId.toString()) return@addListener
                        mediaController.playlistMetadata = MediaMetadata.Builder().setTitle(albumId.toString()).build()
                        mediaController.addListener(object : Player.Listener {
                            @SuppressLint("CheckResult")
                            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                                mediaItem?.mediaMetadata?.let { metadata ->
                                    viewModel.setState(
                                        title = metadata.title.toString(),
                                        author = metadata.artist.toString(),
                                        album = metadata.albumTitle.toString(),
                                        imageUrl = metadata.artworkUri.toString()
                                    )
                                }
                            }
                        })
                        mediaController.setMediaItems(mediaList)
                        val targetIndex = mediaList.indexOfFirst { it.mediaId == trackId }
                        if (targetIndex >= 0) {
                            mediaController.seekTo(targetIndex, 0)
                        }
                        mediaController.play()
                    },
                    MoreExecutors.directExecutor()
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectLatest {
                binding.titleTextView.text = it.title
                binding.authorTextView.text = it.author
                binding.albumTextView.text = it.album
                val imageUrl = it.imageUrl
                if (imageUrl != null) {
                    Glide.with(requireContext())
                        .load(imageUrl)
                        .centerCrop()
                        .placeholder(R.drawable.placeholder_song)
                        .error(R.drawable.placeholder_song)
                        .into(binding.albumCoverBackground)
                } else {
                    Glide.with(requireContext())
                        .load(R.drawable.placeholder_song)
                        .into(binding.albumCoverBackground)
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        MediaController.releaseFuture(controllerFuture)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        private const val TRACK_ID_KEY = "trackId"
        private const val SOURCE_KEY = "source"
        private const val LOCAL_SOURCE = "local"
        private const val REMOTE_SOURCE = "remote"

        fun newBundleFromApi(trackId: String): Bundle = Bundle().apply {
            putString(TRACK_ID_KEY, trackId)
            putString(SOURCE_KEY, REMOTE_SOURCE)
        }

        fun newBundleFromLocal(trackId: String): Bundle = Bundle().apply {
            putString(TRACK_ID_KEY, trackId)
            putString(SOURCE_KEY, LOCAL_SOURCE)
        }
    }
}