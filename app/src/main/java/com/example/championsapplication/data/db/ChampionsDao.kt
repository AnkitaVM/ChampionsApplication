package com.example.championsapplication.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query

@Dao
interface ChampionsDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertAllChampions(championEntities: List<ChampionEntity>)

    @Query("select * from champion")
    suspend fun getAllChampions(): List<ChampionEntity>

    @Query("select * from champion where id = :championId")
    suspend fun getChampionDetails(championId: String): ChampionEntity?
}