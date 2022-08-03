package com.albertson.acronymsearch.framework.model

import com.albertson.acronymsearch.domain.model.AcronymResponse

data class ViewStateData(
  val isLoading: Boolean = false,
  val isDataAvailable: Boolean = false,
  val data: List<AcronymResponse>? = null,
  val error: Throwable? = null
)