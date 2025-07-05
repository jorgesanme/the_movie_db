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

    @GET(Constants.SEARCH_BY_NAME_END_POINT)
    @Headers("Content-Type: application/json")
    suspend fun getMovieByName(
        @Query("api_key") apiKey: String,
        @Query("region") region: String,
        @Query("query") movieName: String
    ): RemoteResult


    @GET(Constants.GET_GENRES_LIST_END_POINT)
    @Headers("Content-Type: application/json")
    suspend fun getMovieGenres(
        @Query("api_key") apiKey: String,
        @Query("region") region: String
    ): RemoteGenres

}