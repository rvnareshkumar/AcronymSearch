package com.albertson.acronymsearch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.albertson.acronymsearch.data.network.AcronymSearchApi
import com.albertson.acronymsearch.data.repository.AcronymSearchRepositoryImpl
import com.albertson.acronymsearch.domain.model.AcronymResponse
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AcronymSearchRepositoryTest {

  private lateinit var repository: AcronymSearchRepositoryImpl

  @get:Rule
  var coroutineRule = MainCoroutineRule()

  @get:Rule
  val instantRule = InstantTaskExecutorRule()

  private val service: AcronymSearchApi = mock()

  @Before
  fun setup() {
    repository = AcronymSearchRepositoryImpl(service, coroutineRule.testDispatcher)
  }

  @Test(expected = Exception::class)
  fun initCheckout_Error() = coroutineRule.runBlockingTest {
    Mockito.`when`(service.getAcronymSearch(anyString())).thenThrow(RuntimeException())
    repository.getAcronymSearch("HMT")
  }

  @Test
  fun initCheckout_Success() = coroutineRule.runBlockingTest {
    val fileJson = ClassLoader.getSystemResource("acronym_response.json").readText()
    val response = Gson().fromJson(fileJson, AcronymResponse::class.java)
    val mockData = listOf(response)

    Mockito.`when`(service.getAcronymSearch(anyString())).thenReturn(mockData)
    val result = repository.getAcronymSearch("HMT")
    assert(result == mockData)
    assert(result[0].lfs!![0].lf == "histamine N-methyltransferase")
  }
}