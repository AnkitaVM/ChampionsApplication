package com.example.championsapplication.domain.usecases

import com.example.championsapplication.domain.mapper.toListOfUIChampion
import com.example.championsapplication.domain.model.Result
import com.example.championsapplication.domain.repository.ChampionsRepository
import com.example.championsapplication.presentation.uimodels.UIChampion
import javax.inject.Inject

class GetAllChampionsUseCase @Inject constructor(
    private var championsRepository: ChampionsRepository,
) {
    suspend operator fun invoke(): Result<List<UIChampion>> {
        val res = championsRepository.getChampions()
        if (res is Result.Success) {
            res.data?.let {
                val uiChampions = it.toListOfUIChampion()
                return Result.Success(uiChampions)
            }
        }
        return Result.Error(res.errorType)
    }
}