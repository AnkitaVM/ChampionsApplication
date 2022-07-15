package com.example.championsapplication.di

import com.example.championsapplication.data.api.ChampionsApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule(private val baseURL: String) {

    @Provides
    fun provideRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseURL)
            .build()
    }

    @Provides
    fun provideChampionsListService(retrofit: Retrofit): ChampionsApi {
        return retrofit.create(ChampionsApi::class.java)
    }
}