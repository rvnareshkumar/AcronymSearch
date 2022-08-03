package com.albertson.acronymsearch.domain.repository

import com.albertson.acronymsearch.domain.model.AcronymResponse


interface AcronymSearchRepository {
  suspend fun getAcronymSearch(input:String) : List<AcronymResponse>
}