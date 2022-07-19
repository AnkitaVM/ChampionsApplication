package com.example.championsapplication.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.championsapplication.domain.model.Champion
import com.example.championsapplication.domain.model.ChampionImage

@Entity(tableName = TABLE_CHAMPION)
data class ChampionEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
    val id: String,
    @ColumnInfo(name = COLUMN_KEY)
    val key: String,
    @ColumnInfo(name = COLUMN_NAME)
    val name: String,
    @ColumnInfo(name = COLUMN_TITLE)
    val title: String,
    @ColumnInfo(name = COLUMN_BLURB)
    val blurb: String,
    @ColumnInfo(name = COLUMN_IMAGE_FULL)
    val championImagePathFull: String?
) {
    companion object {
        fun fromChampionModel(champion: Champion) = ChampionEntity(
            champion.id, champion.key, champion.name, champion.title, champion.blurb,
            champion.championImage?.full
        )
    }

    fun toChampionModel(): Champion {
        var chImage: ChampionImage? = null
        championImagePathFull?.let {
            chImage = ChampionImage(it, "", "")
        }
        return Champion(id, key, name, title, blurb, chImage)
    }
}