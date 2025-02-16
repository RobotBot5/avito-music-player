package com.robotbot.avito.music_local.presentation.music_local

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.robotbot.avito.muic_list_core.presentation.BaseMusicListFragment
import com.robotbot.avito.music_local.R
import com.robotbot.avito.music_local.di.MusicLocalComponentProvider
import javax.inject.Inject

class MusicLocalFragment : BaseMusicListFragment<MusicLocalViewModel>() {

    private val component by lazy {
        (requireActivity().application as MusicLocalComponentProvider).provideMusicLocalComponentProvider()
    }

    override val title: String by lazy { getString(R.string.title_music_local) }

    @Inject
    internal lateinit var viewModelFactory: MusicLocalViewModelFactory

    override fun obtainViewModel(): MusicLocalViewModel =
        ViewModelProvider(this, viewModelFactory)[MusicLocalViewModel::class.java]

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }
}