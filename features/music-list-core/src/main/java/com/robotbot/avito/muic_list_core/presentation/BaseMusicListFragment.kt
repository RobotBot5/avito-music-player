package com.robotbot.avito.muic_list_core.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import com.robotbot.avito.common.simpleScan
import com.robotbot.avito.muic_list_core.databinding.FragmentBaseMusicListBinding
import com.robotbot.avito.muic_list_core.domain.entities.SongToDisplay
import com.robotbot.avito.muic_list_core.presentation.adapter.DefaultLoadStateAdapter
import com.robotbot.avito.muic_list_core.presentation.adapter.MusicPagingAdapter
import com.robotbot.avito.muic_list_core.presentation.adapter.TryAgainAction
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

abstract class BaseMusicListFragment<VM : BaseMusicListViewModel> : Fragment() {

    private var _binding: FragmentBaseMusicListBinding? = null
    private val binding: FragmentBaseMusicListBinding
        get() = _binding ?: throw RuntimeException("FragmentBaseMusicListBinding == null")

    protected abstract val title: String

    private lateinit var mainLoadStateHolder: DefaultLoadStateAdapter.Holder

    private lateinit var adapter: MusicPagingAdapter

    protected val viewModel: VM by lazy {
        obtainViewModel()
    }

    protected abstract fun obtainViewModel(): VM

    protected open fun onDownloadClickListener(songToDisplay: SongToDisplay) {}

    protected open fun onDeleteClickListener(songToDisplay: SongToDisplay) {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBaseMusicListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupRecyclerView()
        observeViewModel()
    }

    private fun setupView() {
        binding.searchInputEditText.addTextChangedListener {
            viewModel.setNewSearchQuery(it.toString())
        }
        binding.topToolBar.title = title
    }

    private fun setupRecyclerView() {
        adapter = MusicPagingAdapter()

        adapter.onDownloadClickListener = {
            onDownloadClickListener(it)
        }
        adapter.onDeleteClickListener = {
            onDeleteClickListener(it)
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

    @OptIn(FlowPreview::class)
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

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.musicList.collectLatest {
                adapter.submitData(it.musicList)
            }
        }
    }

    private fun getRefreshLoadStateFlow(): Flow<LoadState> = adapter.loadStateFlow
        .map { it.refresh }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}