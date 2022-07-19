package com.example.championsapplication.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.championsapplication.domain.model.Champion
import com.example.championsapplication.domain.model.Result
import com.example.championsapplication.domain.usecases.GetAllChampionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChampionsListViewModel @Inject constructor(private var getAllChampionsUseCase: GetAllChampionsUseCase) :
    ViewModel() {

    private val _champions: MutableLiveData<Result<List<Champion>>> = MutableLiveData()
    val champions: LiveData<Result<List<Champion>>> get() = _champions


    fun getAllChampions() =
        viewModelScope.launch(Dispatchers.IO) {
            _champions.postValue(Result.Loading())
            try {
                val championsList = getAllChampionsUseCase()
                _champions.postValue(championsList)
            } catch (e: Exception) {
                _champions.postValue(Result.Error(e.message.toString()))
            }
        }

}