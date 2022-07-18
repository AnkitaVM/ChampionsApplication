package com.example.championsapplication.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.championsapplication.data.model.Champion
import com.example.championsapplication.data.model.Result
import com.example.championsapplication.domain.usecases.GetAllChampionsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChampionsListViewModel(private var getAllChampionsUseCase: GetAllChampionsUseCase) :
    ViewModel() {

    private val _champions: MutableLiveData<Result<List<Champion>>> = MutableLiveData()
    val champions: LiveData<Result<List<Champion>>> get() = _champions


    fun getAllChampions() = viewModelScope.launch(Dispatchers.IO) {
        val championsList = getAllChampionsUseCase()
        _champions.postValue(championsList)
    }

}