package com.jorgesm.themoviedb.model


import com.jorgesm.themoviedb.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query


interface RemoteService {

 @GET(Constants.POPULAR_MOVIES_URL)
 suspend fun listPopularMovies(
  @Query("api_key") apiKey: String,
  @Query("region") region: String
 ): RemoteResult
}