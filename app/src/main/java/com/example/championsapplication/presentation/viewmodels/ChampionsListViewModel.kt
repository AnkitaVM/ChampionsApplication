package com.example.championsapplication.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.championsapplication.domain.model.ErrorType
import com.example.championsapplication.domain.model.Result
import com.example.championsapplication.domain.usecases.GetAllChampionsUseCase
import com.example.championsapplication.presentation.mapper.toListOfUIChampion
import com.example.championsapplication.presentation.uimodels.UIChampion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChampionsListViewModel @Inject constructor(
    private val getAllChampionsUseCase: GetAllChampionsUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _champions: MutableLiveData<Result<List<UIChampion>>> = MutableLiveData()
    val champions: LiveData<Result<List<UIChampion>>> get() = _champions


    fun getAllChampions() =
        viewModelScope.launch(dispatcher) {
            _champions.postValue(Result.Loading())
            try {
                val res = getAllChampionsUseCase()
                if (res is Result.Success) {
                    res.data?.let {
                        val uiChampions = it.toListOfUIChampion()
                        _champions.postValue(Result.Success(uiChampions))
                    }
                } else {
                    _champions.postValue(Result.Error(res.errorType))
                }
            } catch (e: Exception) {
                _champions.postValue(Result.Error(ErrorType.UnknownError))
            }
        }

}