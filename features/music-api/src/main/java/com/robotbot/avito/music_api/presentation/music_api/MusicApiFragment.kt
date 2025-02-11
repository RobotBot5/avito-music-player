package com.robotbot.avito.music_api.presentation.music_api

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.robotbot.avito.music_api.databinding.FragmentMusicApiBinding
import com.robotbot.avito.music_api.di.MusicApiComponentProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

class MusicApiFragment : Fragment() {

    private var _binding: FragmentMusicApiBinding? = null
    private val binding: FragmentMusicApiBinding
        get() = _binding ?: throw RuntimeException("FragmentMusicApiBinding == null")

    private val component by lazy {
        (requireActivity().application as MusicApiComponentProvider).provideMusicApiComponent()
    }

    @Inject
    lateinit var songsAdapter: SongsAdapter

    @Inject
    lateinit var viewModelFactory: MusicApiViewModelFactory

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
        binding.searchInputEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.setNewSearchQuery(text.toString())
        }
    }

    private fun setupRecyclerView() {
        binding.songsRecyclerView.itemAnimator = null
        binding.songsRecyclerView.adapter = songsAdapter
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                with(binding) {
                    when (state) {
                        MusicApiState.Initial -> {}
                        MusicApiState.Loading -> {
                            songsRecyclerView.visibility = View.GONE
                            songsProgressBar.visibility = View.VISIBLE
                        }

                        is MusicApiState.Error -> {

                        }

                        is MusicApiState.ChartMusic -> {
                            songsProgressBar.visibility = View.GONE
                            songsRecyclerView.visibility = View.VISIBLE
                            songsAdapter.submitList(state.songs)
                        }

                        is MusicApiState.SearchMusic -> {
                            songsProgressBar.visibility = View.GONE
                            songsRecyclerView.visibility = View.VISIBLE
                            songsAdapter.submitList(state.songs)
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