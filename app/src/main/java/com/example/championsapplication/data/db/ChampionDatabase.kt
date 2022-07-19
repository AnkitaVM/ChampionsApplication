package com.example.championsapplication.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ChampionEntity::class], version = DATABASE_VERSION, exportSchema = false)
abstract class ChampionDatabase : RoomDatabase() {
    abstract fun getChampionDao(): ChampionsDao
}