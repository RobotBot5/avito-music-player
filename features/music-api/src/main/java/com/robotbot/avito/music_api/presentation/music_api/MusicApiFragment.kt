package com.robotbot.avito.music_api.presentation.music_api

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.robotbot.avito.muic_list_core.domain.entities.SongToDisplay
import com.robotbot.avito.muic_list_core.presentation.BaseMusicListFragment
import com.robotbot.avito.music_api.R
import com.robotbot.avito.music_api.di.MusicApiComponentProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class MusicApiFragment : BaseMusicListFragment<MusicApiViewModel>() {

    private val component by lazy {
        (requireActivity().application as MusicApiComponentProvider).provideMusicApiComponent()
    }

    @Inject
    internal lateinit var viewModelFactory: MusicApiViewModelFactory

    override val title: String by lazy { getString(R.string.title_music_api) }

    override fun obtainViewModel(): MusicApiViewModel =
        ViewModelProvider(this, viewModelFactory)[MusicApiViewModel::class.java]

    override fun onDownloadClickListener(songToDisplay: SongToDisplay) {
        viewModel.downloadSong(songToDisplay.id)
    }

    override fun onSongClickListener(songToDisplay: SongToDisplay) {
        viewModel.startPlayMusic(songToDisplay.id, findNavController())
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

}