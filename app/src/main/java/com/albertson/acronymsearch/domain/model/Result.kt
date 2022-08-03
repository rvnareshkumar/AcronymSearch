package com.albertson.acronymsearch.domain.model

sealed class Result {
  object Loading : Result()
  data class Success(val data: List<AcronymResponse>) : Result()
  data class Error(val error: Throwable) : Result()
}