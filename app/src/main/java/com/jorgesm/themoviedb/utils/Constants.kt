package com.jorgesm.themoviedb.utils

object Constants {
    const val MOVIE = "DetailActivity:movie"
    const val BASE_URL ="https://api.themoviedb.org/3/"
    const val TEST_BASE_URL ="http://localhost:8080"
    const val POPULAR_MOVIES_URL = "discover/movie?sort_by=popularity.desc"
    
    const val IMG_BASE_URL = "https://image.tmdb.org/t/p/"
    const val IMG_780= "w780"
    const val IMG_185= "w185/"

    const val SEARCH_BY_NAME_END_POINT = "search/movie"
    const val GET_GENRES_LIST_END_POINT = "genre/movie/list"
    const val GET_MOVIE_DETAIL_END_POINT = "movie/{movie_id}"
    const val GET_MOVIES_FILTER_BY_GENRE = "discover/movie"

}