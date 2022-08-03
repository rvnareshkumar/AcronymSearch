package com.albertson.acronymsearch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.albertson.acronymsearch.domain.model.AcronymResponse
import com.albertson.acronymsearch.domain.model.Result
import com.albertson.acronymsearch.domain.repository.AcronymSearchRepository
import com.albertson.acronymsearch.domain.usecase.FetchUseCase
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import java.lang.Exception

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FetchUseCaseTest {

  @get:Rule
  var coroutineRule = MainCoroutineRule()

  @get:Rule
  val instantRule = InstantTaskExecutorRule()

  private lateinit var useCase: FetchUseCase

  private val repository: AcronymSearchRepository = mock()

  @Before
  fun setUp() {
    useCase = FetchUseCase(repository, coroutineRule.testDispatcher)
  }

  @Test
  fun test_Success() = coroutineRule.runBlockingTest {
    val fileJson = ClassLoader.getSystemResource("acronym_response.json").readText()
    val response = Gson().fromJson(fileJson, AcronymResponse::class.java)
    val mockData = listOf(response)
    Mockito.`when`(repository.getAcronymSearch(anyString())).thenReturn(mockData)
    val result = useCase("HMT")
    assert((result as Result.Success).data == mockData)
  }

  @Test
  fun test_Failure() = coroutineRule.runBlockingTest {
    Mockito.`when`(repository.getAcronymSearch(anyString())).thenThrow(RuntimeException("Exception thrown"))
    val result = useCase("HMT")
    MatcherAssert.assertThat(result, CoreMatchers.instanceOf(Result.Error::class.java))
    assert((result as Result.Error).error.message == "Exception thrown")
  }

}