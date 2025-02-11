package com.robotbot.avito.data.music.di

import dagger.Subcomponent

@Subcomponent(
    modules = [
        ApiModule::class,
        MusicRepositoriesDataModule::class,
        MusicSourcesModule::class
    ]
)
interface DataComponent {
}