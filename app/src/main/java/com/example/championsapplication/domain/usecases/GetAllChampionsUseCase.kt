package com.example.championsapplication.domain.usecases

import com.example.championsapplication.domain.model.Champion
import com.example.championsapplication.domain.model.Result
import com.example.championsapplication.domain.repository.ChampionsRepository
import com.example.championsapplication.presentation.uimodels.UIChampion
import com.example.championsapplication.utils.ListMapper
import com.example.championsapplication.utils.ListMapperImpl
import javax.inject.Inject

class GetAllChampionsUseCase @Inject constructor(
    private var championsRepository: ChampionsRepository,
    private val listMapperImpl: ListMapper<Champion, UIChampion>
) {
    suspend operator fun invoke(): Result<List<UIChampion>> {
        val res = championsRepository.getChampions()
        if (res is Result.Success) {
            res.data?.let {
                val n = listMapperImpl.map(it)
                return Result.Success(n)
            }
        }
        return Result.Error("Empty list")
    }
}

