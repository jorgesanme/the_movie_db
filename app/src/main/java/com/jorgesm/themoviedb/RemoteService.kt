package com.jorgesm.themoviedb


import retrofit2.http.GET
import retrofit2.http.Query


interface RemoteService {

 @GET(Constans.POPULAR_MOVIES_URL)
 suspend fun listPopularMovies(@Query("api_key")apiKey:String): RemoteResult
}