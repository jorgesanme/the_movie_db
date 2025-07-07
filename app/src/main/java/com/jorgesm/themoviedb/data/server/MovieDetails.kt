package com.jorgesm.themoviedb.data.server

import com.google.gson.annotations.SerializedName

data class MovieDetails(
    var adult: Boolean?,
    @SerializedName("backdrop_path")
    var backdropPath: String?,
    @SerializedName("belongs_to_collection")
    var belongsToCollection: String,
    var budget: Int,
    var genres: List<Genre>?,
    var homepage: String?,
    var id: Int?,
    @SerializedName("imdb_id")
    var imdbId: String?,
    @SerializedName("origin_country")
    var originCountry: List<String>?,
    @SerializedName("original_language")
    var originalLanguage: String?,
    @SerializedName("original_title")
    var originalTitle: String?,
    var overview: String?,
    var popularity: Double?,
    @SerializedName("poster_path")
    var posterPath: String?,
    @SerializedName("production_companies")
    var productionCompanies: List<ProductionCompany>?,
    @SerializedName("production_countries")
    var productionCountries: List<ProductionCountry>?,
    @SerializedName("release_date")
    var releaseDate: String,
    var revenue: Int,
    var runtime: Int?,
    @SerializedName("spoken_languages")
    var spokenLanguages: List<SpokenLanguage>?,
    var status: String?,
    var tagline: String?,
    var title: String?,
    var video: Boolean,
    @SerializedName("vote_average")
    var voteAverage: Double,
    @SerializedName("vote_count")
    var voteCount: Int
)