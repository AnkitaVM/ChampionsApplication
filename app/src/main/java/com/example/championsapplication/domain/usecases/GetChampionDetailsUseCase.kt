package com.example.championsapplication.domain.usecases

import com.example.championsapplication.domain.repository.ChampionsRepository
import javax.inject.Inject

class GetChampionDetailsUseCase @Inject constructor(private val championsRepository: ChampionsRepository) {
    suspend fun execute(chId: String) = championsRepository.getChampionDetails(chId)
}
