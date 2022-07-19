package com.example.championsapplication.presentation.di

import com.example.championsapplication.data.repository.ChampionRepositoryImpl
import com.example.championsapplication.domain.repository.ChampionsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class ChampionsRepositoryModule {

    @Binds
    abstract fun bindRepository(championRepositoryImpl: ChampionRepositoryImpl): ChampionsRepository
}