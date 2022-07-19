package com.example.championsapplication.presentation.di

import com.example.championsapplication.presentation.ChampionsRecyclerViewAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewWithFragmentComponent

@InstallIn(ViewWithFragmentComponent::class)
@Module
class ChampionAdapterModule {
    @Provides
    fun provideChampionsListAdapter(): ChampionsRecyclerViewAdapter {
        return ChampionsRecyclerViewAdapter()
    }
}