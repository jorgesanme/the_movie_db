package com.jorgesm.themoviedb.model


import com.jorgesm.themoviedb.Constans
import retrofit2.http.GET
import retrofit2.http.Query


interface RemoteService {

 @GET(Constans.POPULAR_MOVIES_URL)
 suspend fun listPopularMovies(
  @Query("api_key") apiKey: String,
  @Query("region") region: String
 ): RemoteResult
}