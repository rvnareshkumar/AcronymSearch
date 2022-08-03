package com.albertson.acronymsearch.domain.usecase


import com.albertson.acronymsearch.domain.model.Result
import javax.inject.Inject
import com.albertson.acronymsearch.domain.repository.AcronymSearchRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext


class FetchUseCase @Inject constructor(
  private val repository: AcronymSearchRepository,
  private val ioDispatcher: CoroutineDispatcher
) {

  suspend operator fun invoke(input :String ) : Result {
  return  try {
      withContext(ioDispatcher) {
        val response  = repository.getAcronymSearch(input)
          Result.Success(response)
        }
    } catch (e: Exception) {
      Result.Error(e)
    }
  }
}