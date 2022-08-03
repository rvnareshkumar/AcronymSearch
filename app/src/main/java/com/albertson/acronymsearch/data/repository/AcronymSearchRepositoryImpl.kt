package com.albertson.acronymsearch.data.repository

import com.albertson.acronymsearch.data.network.AcronymSearchApi
import com.albertson.acronymsearch.domain.model.AcronymResponse
import com.albertson.acronymsearch.domain.repository.AcronymSearchRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AcronymSearchRepositoryImpl @Inject constructor(
  private val searchApi: AcronymSearchApi,
  private val ioDispatcher: CoroutineDispatcher
) : AcronymSearchRepository {

  override suspend fun getAcronymSearch(input:String): List<AcronymResponse> {
    return withContext(ioDispatcher) {
      searchApi.getAcronymSearch(input)
    }
  }
}
