package com.albertson.acronymsearch.framework.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albertson.acronymsearch.domain.model.AcronymResponse
import com.albertson.acronymsearch.domain.model.Result
import com.albertson.acronymsearch.domain.usecase.FetchUseCase
import com.albertson.acronymsearch.framework.model.ViewStateData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AcronymViewModel @Inject constructor(val useCase: FetchUseCase) :
  ViewModel() {

  var job:Job? = null

  private val _state = MutableLiveData<ViewStateData>()

  val currentState: LiveData<ViewStateData> = _state

  fun fetchData(param: String) {
    job?.cancel()
    job = viewModelScope.launch {
      updateViewWithStateObject(Result.Loading)
      updateViewWithStateObject(useCase(param))
    }
  }

  private fun updateViewWithStateObject(result: Result) {
    when (result) {
      is Result.Success -> {
        _state.value = ViewStateData(isDataAvailable = true, data = result.data)
      }
      is Result.Error -> {
        _state.value = ViewStateData(error = result.error)
      }
      is Result.Loading -> {
        _state.value = ViewStateData(isLoading = true)
      }
    }
  }
}