package com.example.championsapplication.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.championsapplication.domain.model.Champion
import com.example.championsapplication.domain.model.Result
import com.example.championsapplication.domain.usecases.GetChampionDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChampionsDetailsViewModel @Inject constructor(private val getChampionDetailsUseCase: GetChampionDetailsUseCase) :
    ViewModel() {

    private val _champion: MutableLiveData<Result<Champion>> = MutableLiveData()
    val champion: LiveData<Result<Champion>> get() = _champion


    fun getChampionDetails(chId: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            _champion.postValue(Result.Loading())
            _champion.postValue(getChampionDetailsUseCase(chId))
        } catch (e: Exception) {
            _champion.postValue(Result.Error(e.message.toString()))
        }

    }
}