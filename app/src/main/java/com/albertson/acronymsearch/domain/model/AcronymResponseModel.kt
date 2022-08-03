package com.albertson.acronymsearch.domain.model

import com.google.gson.annotations.SerializedName

data class AcronymResponse(
  @SerializedName("sf")
  var shortForm : String? = null,
  @SerializedName("lfs")
  var longFormList : ArrayList<LongForm>? = null
)

data class LongForm(
  @SerializedName("lf")
  var longFormText: String? = null,
  var freq: Int = 0,
  var since: Int = 0
)