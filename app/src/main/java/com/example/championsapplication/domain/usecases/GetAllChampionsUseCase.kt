package com.example.championsapplication.domain.usecases

import com.example.championsapplication.domain.repository.ChampionsRepository
import javax.inject.Inject

class GetAllChampionsUseCase @Inject constructor(
    private var championsRepository: ChampionsRepository,
) {
    suspend operator fun invoke() = championsRepository.getChampions()
}