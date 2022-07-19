package com.example.championsapplication.presentation.di

import android.content.Context
import androidx.room.Room
import com.example.championsapplication.data.db.ChampionDatabase
import com.example.championsapplication.data.db.ChampionsDao
import com.example.championsapplication.data.db.DATABASE_CHAMPIONS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DbModule {
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