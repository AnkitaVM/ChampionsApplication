package com.example.championsapplication.domain.usecases

import com.example.championsapplication.domain.model.ErrorType
import com.example.championsapplication.domain.model.Result
import com.example.championsapplication.domain.repository.ChampionsRepository
import com.example.championsapplication.domain.mapper.toUIChampion
import com.example.championsapplication.domain.model.uimodels.UIChampion
import javax.inject.Inject

class GetChampionDetailsUseCase @Inject constructor(
    private val championsRepository: ChampionsRepository,
) {
    suspend operator fun invoke(chId: String): Result<UIChampion> {
        val res = championsRepository.getChampionDetails(chId)
        if (res is Result.Success) {
            res.data?.let {
                return Result.Success(it.toUIChampion())
            }
        }
        return Result.Error(ErrorType.UnknownError)
    }
}
