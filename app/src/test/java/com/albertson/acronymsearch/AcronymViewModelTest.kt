package com.albertson.acronymsearch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.albertson.acronymsearch.domain.model.AcronymResponse
import com.albertson.acronymsearch.domain.model.Result
import com.albertson.acronymsearch.domain.usecase.FetchUseCase
import com.albertson.acronymsearch.framework.presenter.AcronymViewModel
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AcronymViewModelTest {

  @get:Rule
  var coroutineRule = MainCoroutineRule()

  @get:Rule
  val instantRule = InstantTaskExecutorRule()

  private val useCase: FetchUseCase = mock()

  private lateinit var viewModel: AcronymViewModel


  @Before
  fun setUp() {
    viewModel = AcronymViewModel(useCase)
  }

  @Test
  fun test_fetchData_Success() {
    val fileJson = ClassLoader.getSystemResource("acronym_response.json").readText()
    val response = Gson().fromJson(fileJson, AcronymResponse::class.java)
    val mockData = listOf(response)

    coroutineRule.runBlockingTest {
      Mockito.`when`(useCase.invoke(anyString())).thenReturn(Result.Success(mockData))
    }
    viewModel.fetchData("HMT")
    viewModel.currentState.observeForever {
      assert(it.data!!.isEmpty().not())
    }
  }

  @Test
  fun test_fetchData_Error() {
    coroutineRule.runBlockingTest {
      Mockito.`when`(useCase.invoke(anyString())).thenReturn(Result.Error(Exception()))
    }
    viewModel.fetchData("HMT")
    viewModel.currentState.observeForever {
      assert(it.data.isNullOrEmpty())
    }
  }
}