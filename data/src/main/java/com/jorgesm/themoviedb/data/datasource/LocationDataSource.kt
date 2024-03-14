package com.jorgesm.themoviedb.data.datasource



interface LocationDataSource {
    suspend fun findLastLocation(): String?
}

