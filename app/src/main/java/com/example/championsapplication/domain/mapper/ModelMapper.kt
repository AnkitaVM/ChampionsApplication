package com.example.championsapplication.domain.mapper

import com.example.championsapplication.data.db.ChampionEntity
import com.example.championsapplication.domain.model.Champion
import com.example.championsapplication.domain.model.ChampionImage
import com.example.championsapplication.presentation.uimodels.UIChampion
import com.example.championsapplication.presentation.uimodels.UIChampionImage

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

fun ChampionImage.toUIChampionImage(championImage: ChampionImage): UIChampionImage {
    return UIChampionImage(
        championImage.full,
        championImage.sprite,
        championImage.group,
    )
}

fun Champion.toUIChampion(): UIChampion {
    return UIChampion(
        this.id,
        this.key,
        this.name,
        this.title,
        this.blurb,
        this.championImage?.let { it.toUIChampionImage(it) }
    )
}

fun List<Champion>.toListOfUIChampion(): List<UIChampion> {
    return this.map {
        it.toUIChampion()
    }
}