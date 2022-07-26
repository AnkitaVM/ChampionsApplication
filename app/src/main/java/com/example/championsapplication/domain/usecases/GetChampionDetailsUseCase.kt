package com.example.championsapplication.domain.usecases

import com.example.championsapplication.domain.model.ErrorType
import com.example.championsapplication.domain.model.Result
import com.example.championsapplication.domain.repository.ChampionsRepository
import com.example.championsapplication.presentation.uimodels.UIChampion
import com.example.championsapplication.utils.ChampionUiModelMapper
import javax.inject.Inject

class GetChampionDetailsUseCase @Inject constructor(
    private val championsRepository: ChampionsRepository,
    private val championUiModelMapper: ChampionUiModelMapper
) {
    suspend operator fun invoke(chId: String): Result<UIChampion> {
        val res = championsRepository.getChampionDetails(chId)
        if (res is Result.Success) {
            res.data?.let {
                return Result.Success(championUiModelMapper.map(it))
            }
        }
        return Result.Error(ErrorType.UnknownError)
    }
}
