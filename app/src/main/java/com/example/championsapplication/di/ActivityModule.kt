package com.example.championsapplication.di

import android.content.Context
import androidx.room.Room
import com.example.championsapplication.BuildConfig
import com.example.championsapplication.data.api.ChampionsApi
import com.example.championsapplication.data.db.ChampionDatabase
import com.example.championsapplication.data.db.ChampionsDao
import com.example.championsapplication.data.db.DATABASE_CHAMPIONS
import com.example.championsapplication.data.repository.ChampionRepositoryImpl
import com.example.championsapplication.domain.repository.ChampionsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(ActivityRetainedComponent::class)
@Module
class ActivityModule {

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

    @Provides
    fun provideRepository(championRepositoryImpl: ChampionRepositoryImpl): ChampionsRepository {
        return championRepositoryImpl
    }

    @Provides
    fun provideChampionsDao(@ApplicationContext context: Context): ChampionsDao {
        return buildDatabaseInstance(context).getChampionDao()
    }

    @Provides
    fun buildDatabaseInstance(context: Context): ChampionDatabase {
        return Room.databaseBuilder(context, ChampionDatabase::class.java, DATABASE_CHAMPIONS)
            .fallbackToDestructiveMigration().build()
    }
}