package com.example.championsapplication.di

import com.example.championsapplication.domain.model.Champion
import com.example.championsapplication.presentation.uimodels.UIChampion
import com.example.championsapplication.utils.ChampionUiModelMapper
import com.example.championsapplication.utils.ListMapper
import com.example.championsapplication.utils.ListMapperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@InstallIn(ViewModelComponent::class)
@Module
class ViewModelModule {
    @Provides
    fun provideIODispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Provides
    fun provideListUiChampionModelMapper(championUiModelMapper: ChampionUiModelMapper): ListMapper<Champion, UIChampion> {
        return ListMapperImpl(championUiModelMapper)
    }

}