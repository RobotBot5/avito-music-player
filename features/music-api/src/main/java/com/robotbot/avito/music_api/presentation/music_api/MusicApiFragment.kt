package com.robotbot.avito.music_api.presentation.music_api

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.offline.DownloadHelper
import androidx.media3.exoplayer.offline.DownloadIndex
import androidx.media3.exoplayer.offline.DownloadManager
import androidx.media3.exoplayer.offline.DownloadRequest
import androidx.media3.exoplayer.offline.DownloadService
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import com.google.gson.Gson
import com.robotbot.avito.common.simpleScan
import com.robotbot.avito.music_api.DownloadMusicService
import com.robotbot.avito.music_api.databinding.FragmentMusicApiBinding
import com.robotbot.avito.music_api.di.MusicApiComponentProvider
import com.robotbot.avito.music_api.presentation.music_api.adapter.DefaultLoadStateAdapter
import com.robotbot.avito.music_api.presentation.music_api.adapter.MusicPagingAdapter
import com.robotbot.avito.music_api.presentation.music_api.adapter.TryAgainAction
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class MusicApiFragment : Fragment() {

    private var _binding: FragmentMusicApiBinding? = null
    private val binding: FragmentMusicApiBinding
        get() = _binding ?: throw RuntimeException("FragmentMusicApiBinding == null")

    private val component by lazy {
        (requireActivity().application as MusicApiComponentProvider).provideMusicApiComponent()
    }

    private lateinit var mainLoadStateHolder: DefaultLoadStateAdapter.Holder

    private lateinit var adapter: MusicPagingAdapter

    @Inject
    internal lateinit var viewModelFactory: MusicApiViewModelFactory

    private val viewModel: MusicApiViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MusicApiViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMusicApiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListenersOnViews()
        setupRecyclerView()
        observeViewModel()
    }

    private fun setListenersOnViews() {
        binding.searchInputEditText.addTextChangedListener {
            viewModel.setNewSearchQuery(it.toString())
        }
    }

    @UnstableApi
    private fun setupRecyclerView() {
        adapter = MusicPagingAdapter()

        adapter.onDownloadClickListener = { song ->
            val downloadRequest = DownloadRequest.Builder(
                song.id.toString(),
                Uri.parse(song.previewUrl)
            ).setData(Gson().toJson(song).toByteArray()).build()

//            DownloadService.start(
//                requireContext(),
//                DownloadMusicService::class.java
//            )

            DownloadService.sendAddDownload(
                requireContext(),
                DownloadMusicService::class.java,
                downloadRequest,
                false
            )
        }

        val tryAgainAction: TryAgainAction = { adapter.retry() }

        val footerAdapter = DefaultLoadStateAdapter(tryAgainAction)
        val adapterWithLoadState = adapter.withLoadStateFooter(footerAdapter)

        binding.songsRecyclerView.adapter = adapterWithLoadState
        (binding.songsRecyclerView.itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations =
            false

        mainLoadStateHolder = DefaultLoadStateAdapter.Holder(
            binding.loadStateView,
            tryAgainAction
        )

        observeLoadState()

        handleScrollingToTopWhenSearching()
        handleListVisibility()
    }

    private fun observeLoadState() {
        viewLifecycleOwner.lifecycleScope.launch {
            adapter.loadStateFlow.debounce(200).collectLatest { state ->
                mainLoadStateHolder.bind(state.refresh)
            }
        }
    }

    private fun handleScrollingToTopWhenSearching() {
        viewLifecycleOwner.lifecycleScope.launch {
            getRefreshLoadStateFlow()
                .simpleScan(count = 2)
                .collectLatest { (previousState, currentState) ->
                    if (previousState is LoadState.Loading && currentState is LoadState.NotLoading) {
                        binding.songsRecyclerView.scrollToPosition(0)
                    }
                }
        }
    }

    private fun handleListVisibility() = lifecycleScope.launch {
        getRefreshLoadStateFlow()
            .simpleScan(count = 3)
            .collectLatest { (beforePrevious, previous, current) ->
                binding.songsRecyclerView.isInvisible = current is LoadState.Error
                        || previous is LoadState.Error
                        || (beforePrevious is LoadState.Error && previous is LoadState.NotLoading
                        && current is LoadState.Loading)
            }
    }

    private fun getRefreshLoadStateFlow(): Flow<LoadState> = adapter.loadStateFlow
        .map { it.refresh }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                with(binding) {
                    adapter.submitData(state.musicList)
                    when (state.displayState) {
                        MusicApiDisplayState.ChartMusic -> {

                        }

                        is MusicApiDisplayState.Error -> {

                        }

                        MusicApiDisplayState.Initial -> {

                        }

                        MusicApiDisplayState.Loading -> {

                        }

                        MusicApiDisplayState.SearchMusic -> {

                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}