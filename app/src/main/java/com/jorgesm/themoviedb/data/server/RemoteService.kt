package com.jorgesm.themoviedb.data.server


import com.jorgesm.themoviedb.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query


interface RemoteService {

 @GET(Constants.POPULAR_MOVIES_URL)
 suspend fun listPopularMovies(
  @Query("api_key") apiKey: String,
  @Query("region") region: String
 ): RemoteResult

 @GET(Constants.SEARCH_BY_NAME)
 @Headers("Content-Type: application/json")
 suspend fun getMovieByName(
  @Header("authorization") bearer: String? = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmNjE5ZWJiMDliZjFiNDRhODJlNmIxM2RmZGM5YWQ1MCIsIm5iZiI6MTY3ODM3OTM4Mi4wMTEwMDAyLCJzdWIiOiI2NDBhMDk3NjU0ZjZlYjAwZDc4YzBlNjciLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.I3ynmWyKyzMTqu5Pl-rrTDcenV0UN1lIZ8yKebeTinA",
  @Query("api_key") apiKey: String,
  @Query("region") region: String,
  @Query("query") movieName: String
 ): RemoteResult
}