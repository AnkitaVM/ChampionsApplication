package com.example.championsapplication.domain.mapper

import com.example.championsapplication.domain.model.Champion
import com.example.championsapplication.domain.model.ChampionImage
import com.example.championsapplication.domain.model.uimodels.UIChampion
import com.example.championsapplication.domain.model.uimodels.UIChampionImage

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