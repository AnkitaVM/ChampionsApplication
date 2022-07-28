package com.example.championsapplication.domain.mappers

import com.example.championsapplication.data.db.ChampionEntity
import com.example.championsapplication.domain.model.Champion
import com.example.championsapplication.domain.model.ChampionImage
import javax.inject.Inject

class ChampionModelMapper @Inject constructor() : Mapper<ChampionEntity, Champion> {
    override fun map(input: ChampionEntity): Champion {
        var chImage: ChampionImage? = null
        input.championImagePathFull?.let {
            chImage = ChampionImage(it, "", "")
        }
        return Champion(input.id, input.key, input.name, input.title, input.blurb, chImage)
    }
}