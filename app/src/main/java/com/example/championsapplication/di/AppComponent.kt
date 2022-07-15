package com.example.championsapplication.di

import com.example.championsapplication.presentation.ChampionsDetailsFragment
import com.example.championsapplication.presentation.ChampionsListFragment
import dagger.Component

@Component(modules = [ApiModule::class, ChampionsRepositoryModule::class, DbModule::class])
interface AppComponent {
    fun inject(championsListFragment: ChampionsListFragment)
    fun inject(championsDetailsFragment: ChampionsDetailsFragment)
}