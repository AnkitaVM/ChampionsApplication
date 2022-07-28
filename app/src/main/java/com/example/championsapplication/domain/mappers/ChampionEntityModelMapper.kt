package com.example.championsapplication.domain.mappers

import com.example.championsapplication.data.db.ChampionEntity
import com.example.championsapplication.domain.model.Champion
import javax.inject.Inject

class ChampionEntityModelMapper @Inject constructor() : Mapper<Champion, ChampionEntity> {
    override fun map(input: Champion): ChampionEntity {
        return ChampionEntity(
            input.id,
            input.key,
            input.name,
            input.title,
            input.blurb,
            input.championImage?.full
        )
    }
}