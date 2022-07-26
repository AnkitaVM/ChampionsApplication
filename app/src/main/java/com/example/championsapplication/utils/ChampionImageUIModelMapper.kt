package com.example.championsapplication.utils

import com.example.championsapplication.domain.model.ChampionImage
import com.example.championsapplication.presentation.uimodels.UIChampionImage
import javax.inject.Inject

class ChampionImageUIModelMapper @Inject constructor() : Mapper<ChampionImage, UIChampionImage> {
    override fun map(input: ChampionImage): UIChampionImage {
        return UIChampionImage(
            input.full,
            input.sprite,
            input.group,
        )
    }
}