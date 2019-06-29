package com.github.ramannada.movie.network.response

import com.google.gson.annotations.SerializedName

/**
 * Created by labibmuhajir on 2019-06-29.
 * labibmuhajir@yahoo.com
 */
data class MovieDetail(
    val adult: Boolean?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("belongs_to_collection")
    val belongsToCollection: BelongsToCollection?,
    val budget: Int?,
    val genres: List<Genre>?,
    val homepage: String?,
    val id: Int?,
    @SerializedName("imdb_id")
    val imdbDd: String?,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("original_title")
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany?>?,
    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountry?>?,
    @SerializedName("release_date")
    val releaseDate: String?,
    val revenue: Int?,
    val runtime: Int?,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage?>?,
    val status: String?,
    val tagline: String?,
    val title: String?,
    val video: Boolean?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?
) {
    companion object {
        fun movieToMovieDetail(movie: Movie) = MovieDetail(
            movie.adult,
            movie.backdropPath,
            null,
            null,
            null,
            null,
            movie.id,
            null,
            movie.originalLanguage,
            movie.originalTitle,
            movie.overview,
            movie.popularity,
            movie.posterPath,
            null,
            null,
            movie.releaseDate,
            null,
            null,
            null,
            null,
            null,
            movie.title,
            movie.video,
            movie.voteAverage,
            movie.voteCount
        )
    }
}