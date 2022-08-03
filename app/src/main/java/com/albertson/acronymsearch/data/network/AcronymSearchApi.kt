package com.albertson.acronymsearch.data.network


import com.albertson.acronymsearch.domain.model.AcronymResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AcronymSearchApi {
  @GET("dictionary.py")
  suspend fun getAcronymSearch(@Query("sf")shortForm:String): List<AcronymResponse>
}
