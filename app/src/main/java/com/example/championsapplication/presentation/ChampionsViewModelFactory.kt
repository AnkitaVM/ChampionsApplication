package com.example.championsapplication.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.championsapplication.domain.usecases.GetAllChampionsUseCase
import javax.inject.Inject

class ChampionsViewModelFactory @Inject constructor(private val getAllChampionsUseCase: GetAllChampionsUseCase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChampionsListViewModel::class.java)) {
            return ChampionsListViewModel(getAllChampionsUseCase) as T
        }
        throw IllegalArgumentException("ViewModel Not Found")
    }
}