package com.example.championsapplication.di

import com.example.championsapplication.data.datasource.ApiDataSource
import com.example.championsapplication.data.repository.ChampionRepositoryImpl
import com.example.championsapplication.domain.repository.ChampionsRepository
import dagger.Binds
import dagger.Module

@Module
abstract class ChampionsRepositoryModule {

    @Binds
    abstract fun bindRepository(championRepositoryImpl: ChampionRepositoryImpl): ChampionsRepository
}