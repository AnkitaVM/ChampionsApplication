package com.example.championsapplication.data.mapper

import com.example.championsapplication.data.db.ChampionEntity
import com.example.championsapplication.domain.model.Champion
import com.example.championsapplication.domain.model.ChampionImage

fun ChampionEntity.toChampion(championEntity: ChampionEntity): Champion {
    var chImage: ChampionImage? = null
    championEntity.championImagePathFull?.let {
        chImage = ChampionImage(it, "", "")
    }
    return Champion(
        championEntity.id,
        championEntity.key,
        championEntity.name,
        championEntity.title,
        championEntity.blurb,
        chImage
    )
}

fun Champion.toChampionEntity(champion: Champion): ChampionEntity {
    return ChampionEntity(
        champion.id,
        champion.key,
        champion.name,
        champion.title,
        champion.blurb,
        champion.championImage?.full
    )
}