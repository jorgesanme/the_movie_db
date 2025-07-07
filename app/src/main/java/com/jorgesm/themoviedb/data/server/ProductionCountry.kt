package com.jorgesm.themoviedb.data.server

import com.google.gson.annotations.SerializedName

data class ProductionCountry(
    @SerializedName("iso_3166_1")
    var iso31661: String?,
    var name: String?
)