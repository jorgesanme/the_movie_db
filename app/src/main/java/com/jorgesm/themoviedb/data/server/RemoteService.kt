package com.jorgesm.themoviedb.data.server


import com.jorgesm.themoviedb.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
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

    @GET(Constants.GET_MOVIE_DETAIL_END_POINT)
    @Headers("Content-Type: application/json")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("region") region: String
    ): MovieDetails

    @GET(Constants.GET_MOVIES_FILTER_BY_GENRE)
    @Headers("Content-Type: application/json")
    suspend fun getMoviesFilterByGenre(
        @Query("api_key") apiKey: String,
        @Query("region") region: String,
        @Query("with_genres") genres: String
    ): RemoteResult



}
//https://api.themoviedb.org/3/movie/movie_id?language=en-US")
//https://api.themoviedb.org/3/movie/{movie_id}