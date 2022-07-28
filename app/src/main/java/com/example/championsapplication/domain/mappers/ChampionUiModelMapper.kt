package com.example.championsapplication.domain.mappers

import com.example.championsapplication.domain.model.Champion
import com.example.championsapplication.presentation.uimodels.UIChampion
import javax.inject.Inject

class ChampionUiModelMapper @Inject constructor(
    private val championImageUIModelMapper: ChampionImageUIModelMapper
) : Mapper<Champion, UIChampion> {
    override fun map(input: Champion): UIChampion {
        return UIChampion(
            input.id,
            input.key,
            input.name,
            input.title,
            input.blurb,
            input.championImage?.let { championImageUIModelMapper.map(it) }
        )
    }
}