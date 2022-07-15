package com.example.championsapplication.di

import android.content.Context
import androidx.room.Room
import com.example.championsapplication.data.db.ChampionDatabase
import com.example.championsapplication.data.db.ChampionsDao
import com.example.championsapplication.data.db.DATABASE_CHAMPIONS
import dagger.Module
import dagger.Provides

@Module
class DbModule(var context: Context) {
    @Provides
    fun provideChampionsDao(): ChampionsDao {
        return buildDatabaseInstance().getChampionDao()
    }

    @Provides
    fun buildDatabaseInstance(): ChampionDatabase {
        return Room.databaseBuilder(context, ChampionDatabase::class.java, DATABASE_CHAMPIONS)
            .fallbackToDestructiveMigration().build()
    }
}