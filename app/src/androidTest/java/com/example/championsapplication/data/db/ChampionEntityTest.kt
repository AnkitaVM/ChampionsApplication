package com.example.championsapplication.data.db

import com.example.championsapplication.getChampion
import com.example.championsapplication.getChampionEntity
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ChampionEntityTest {

    private lateinit var championEntity: ChampionEntity

    @Before
    fun setUp() {
        championEntity = getChampionEntity("1")
    }

    @Test
    fun fromChampionModel_championModelPassed_correctedChampionEntityReturned() {
        val passedChampionObject = getChampion("1")
        val championEntity = ChampionEntity.fromChampionModel(getChampion("1"))
        Assert.assertEquals(championEntity.id, passedChampionObject.id)
        Assert.assertEquals(championEntity.key, passedChampionObject.key)
        Assert.assertEquals(championEntity.name, passedChampionObject.name)
        Assert.assertEquals(championEntity.title, passedChampionObject.title)
        Assert.assertEquals(championEntity.blurb, passedChampionObject.blurb)
        Assert.assertEquals(
            championEntity.championImagePathFull,
            passedChampionObject.championImage!!.full
        )
    }

    @Test
    fun toChampionModel_convertedIntoChampionModel_correctChampionReturned() {
        val championObject = championEntity.toChampionModel()
        Assert.assertEquals(championEntity.id, championObject.id)
        Assert.assertEquals(championEntity.key, championObject.key)
        Assert.assertEquals(championEntity.name, championObject.name)
        Assert.assertEquals(championEntity.title, championObject.title)
        Assert.assertEquals(championEntity.blurb, championObject.blurb)
        Assert.assertEquals(
            championEntity.championImagePathFull,
            championObject.championImage!!.full
        )
    }
}