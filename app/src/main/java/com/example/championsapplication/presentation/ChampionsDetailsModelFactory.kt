package com.example.championsapplication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.championsapplication.domain.usecases.GetChampionDetailsUseCase
import javax.inject.Inject

class ChampionsDetailsModelFactory @Inject constructor(private val getChampionDetailsUseCase: GetChampionDetailsUseCase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChampionsDetailsViewModel::class.java)) {
            return ChampionsDetailsViewModel(getChampionDetailsUseCase) as T
        }
        throw IllegalArgumentException("ViewModel Not Found")
    }
}