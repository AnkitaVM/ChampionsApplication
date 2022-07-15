package com.example.championsapplication.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.championsapplication.data.model.Champion
import com.example.championsapplication.data.model.Result
import com.example.championsapplication.domain.usecases.GetChampionDetailsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChampionsDetailsViewModel(private val getChampionDetailsUseCase: GetChampionDetailsUseCase) :
    ViewModel() {

    private val _champion: MutableLiveData<Result<Champion>> = MutableLiveData()
    val champion: LiveData<Result<Champion>>
        get() {
            return _champion
        }

    fun getChampionDetails(chId: String) = viewModelScope.launch(Dispatchers.IO) {
        _champion.postValue(getChampionDetailsUseCase.execute(chId))
    }
}