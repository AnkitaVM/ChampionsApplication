package com.example.championsapplication.presentation.di

import com.example.championsapplication.BuildConfig
import com.example.championsapplication.data.api.ChampionsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
class ApiModule{

    @Provides
    fun provideRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }

    @Provides
    fun provideChampionsListService(retrofit: Retrofit): ChampionsApi {
        return retrofit.create(ChampionsApi::class.java)
    }
}