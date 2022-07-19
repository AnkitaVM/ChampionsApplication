package com.example.championsapplication

import com.example.championsapplication.data.db.ChampionEntity
import com.example.championsapplication.domain.model.Champion
import com.example.championsapplication.domain.model.ChampionImage
import com.example.championsapplication.domain.model.ChampionListResponse
import com.example.championsapplication.domain.model.Result
import com.google.gson.Gson
import retrofit2.Response

fun getChampionsList(): List<Champion> {
    val championsList = mutableListOf<Champion>()
    championsList.add(getChampion("1"))
    championsList.add(getChampion("2"))
    return championsList
}

fun getChampion(num: String): Champion {
    return Champion(
        num,
        "key$num",
        "name1$num",
        "title1$num",
        "blurb1$num",
        ChampionImage("full1$num", "", "")
    )

}

fun getSuccessMockResponse(): Response<ChampionListResponse> {
    val championListResponse = getChampionListResponseModel()
    return Response.success(championListResponse)
}

private fun getChampionListResponseModel(): ChampionListResponse {
    val mockResponse =
        "{\"type\":\"champion\",\"format\":\"standAloneComplex\",\"version\":\"12.9.1\",\"data\":{\"Aatrox\":{\"version\":\"12.9.1\",\"id\":\"Aatrox\",\"key\":\"266\",\"name\":\"Aatrox\",\"title\":\"the Darkin Blade\",\"blurb\":\"Once honored defenders of Shurima against the Void, Aatrox and his brethren would eventually become an even greater threat to Runeterra, and were defeated only by cunning mortal sorcery. But after centuries of imprisonment, Aatrox was the first to find...\",\"info\":{\"attack\":8,\"defense\":4,\"magic\":3,\"difficulty\":4},\"image\":{\"full\":\"Aatrox.png\",\"sprite\":\"champion0.png\",\"group\":\"champion\",\"x\":0,\"y\":0,\"w\":48,\"h\":48},\"tags\":[\"Fighter\",\"Tank\"],\"partype\":\"Blood Well\",\"stats\":{\"hp\":580,\"hpperlevel\":90,\"mp\":0,\"mpperlevel\":0,\"movespeed\":345,\"armor\":38,\"armorperlevel\":3.25,\"spellblock\":32,\"spellblockperlevel\":1.25,\"attackrange\":175,\"hpregen\":3,\"hpregenperlevel\":1,\"mpregen\":0,\"mpregenperlevel\":0,\"crit\":0,\"critperlevel\":0,\"attackdamage\":60,\"attackdamageperlevel\":5,\"attackspeedperlevel\":2.5,\"attackspeed\":0.651}}}}"
    val championListResponse = Gson().fromJson(mockResponse, ChampionListResponse::class.java)
    return championListResponse
}

fun getChampionMockResponseModel(): Champion {
    val championResponse =
        "{\"version\":\"12.9.1\",\"id\":\"Aatrox\",\"key\":\"266\",\"name\":\"Aatrox\",\"title\":\"the Darkin Blade\",\"blurb\":\"Once honored defenders of Shurima against the Void, Aatrox and his brethren would eventually become an even greater threat to Runeterra, and were defeated only by cunning mortal sorcery. But after centuries of imprisonment, Aatrox was the first to find...\",\"info\":{\"attack\":8,\"defense\":4,\"magic\":3,\"difficulty\":4},\"image\":{\"full\":\"Aatrox.png\",\"sprite\":\"champion0.png\",\"group\":\"champion\",\"x\":0,\"y\":0,\"w\":48,\"h\":48},\"tags\":[\"Fighter\",\"Tank\"],\"partype\":\"Blood Well\",\"stats\":{\"hp\":580,\"hpperlevel\":90,\"mp\":0,\"mpperlevel\":0,\"movespeed\":345,\"armor\":38,\"armorperlevel\":3.25,\"spellblock\":32,\"spellblockperlevel\":1.25,\"attackrange\":175,\"hpregen\":3,\"hpregenperlevel\":1,\"mpregen\":0,\"mpregenperlevel\":0,\"crit\":0,\"critperlevel\":0,\"attackdamage\":60,\"attackdamageperlevel\":5,\"attackspeedperlevel\":2.5,\"attackspeed\":0.651}}"
    return Gson().fromJson(championResponse, Champion::class.java)
}

fun getResponseInWrappedResultClass(): Result<List<Champion>> {
    val ch = getChampionMockResponseModel()
    return Result.Success(mutableListOf(ch))
}

fun getResponseInWrappedResultClassFromDB(): Result<List<Champion>> {
    val ch = getChampionsList()
    return Result.Success(ch)
}

fun getChampionsEntitiesList(): List<ChampionEntity> {
    val championsList = mutableListOf<ChampionEntity>()
    championsList.add(getChampionEntity("1"))
    championsList.add(getChampionEntity("2"))
    return championsList
}

fun getChampionEntity(num: String): ChampionEntity {
    return ChampionEntity(
        num,
        "key$num",
        "name1$num",
        "title1$num",
        "blurb1$num",
        "full1$num"
    )

}

//fun getErrorMockResponse(): Response<ChampionListResponse> {
//    Response<ChampionListResponse>.isSuccessful = false
//    return Response.error(1, null)
//}