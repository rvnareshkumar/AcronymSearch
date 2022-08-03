package com.albertson.acronymsearch.domain.model

data class AcronymResponse(
  var sf : String? = null,
  var lfs : ArrayList<LongForm>? = null
)

data class LongForm(
  var lf: String? = null,
  var freq: Int? = null,
  var since: Int? = null
)