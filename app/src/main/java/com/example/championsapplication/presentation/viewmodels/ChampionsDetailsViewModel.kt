package com.example.championsapplication.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.championsapplication.domain.model.ErrorType
import com.example.championsapplication.domain.model.Result
import com.example.championsapplication.domain.usecases.GetChampionDetailsUseCase
import com.example.championsapplication.presentation.mapper.toUIChampion
import com.example.championsapplication.presentation.uimodels.UIChampion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChampionsDetailsViewModel @Inject constructor(
    private val getChampionDetailsUseCase: GetChampionDetailsUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _champion: MutableLiveData<Result<UIChampion>> = MutableLiveData()
    val champion: LiveData<Result<UIChampion>> get() = _champion


    fun getChampionDetails(chId: String) = viewModelScope.launch(dispatcher) {
        try {
            _champion.postValue(Result.Loading())
            val res = getChampionDetailsUseCase(chId)
            if (res is Result.Success) {
                res.data?.let {
                    _champion.postValue(Result.Success(it.toUIChampion()))
                }
            } else {
                _champion.postValue(Result.Error(res.errorType))
            }
        } catch (e: Exception) {
            _champion.postValue(Result.Error(ErrorType.UnknownError))
        }

    }
}