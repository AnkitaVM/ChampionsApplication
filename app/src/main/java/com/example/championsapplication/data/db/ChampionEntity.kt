package com.example.championsapplication.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = ChampionTable.TABLE_CHAMPION)
data class ChampionEntity(
    @PrimaryKey
    @ColumnInfo(name = ChampionTable.COLUMN_ID)
    val id: String,
    @ColumnInfo(name = ChampionTable.COLUMN_KEY)
    val key: String,
    @ColumnInfo(name = ChampionTable.COLUMN_NAME)
    val name: String,
    @ColumnInfo(name = ChampionTable.COLUMN_TITLE)
    val title: String,
    @ColumnInfo(name = ChampionTable.COLUMN_BLURB)
    val blurb: String,
    @ColumnInfo(name = ChampionTable.COLUMN_IMAGE_FULL)
    val championImagePathFull: String?
)