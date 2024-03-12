package com.jorgesm.themoviedb.data.datasource


import android.location.Location

interface LocationDataSource {
    suspend fun findLastLocation(): Location?
}

