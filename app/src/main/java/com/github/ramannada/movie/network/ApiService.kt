package com.github.ramannada.movie.network

import com.androidnetworking.AndroidNetworking
import com.github.ramannada.movie.BuildConfig

/**
 * Created by labibmuhajir on 2019-06-29.
 * labibmuhajir@yahoo.com
 */
object ApiService {
    private const val BASE_URL = BuildConfig.MOVIE_BASE_URL
    private const val API_VERSION = BuildConfig.MOVIE_API_VERSION
    private const val ID_KEY = BuildConfig.MOVIE_ID_KEY
    private const val API_KEY = BuildConfig.MOVIE_API_KEY

    private const val URL = "$BASE_URL/$API_VERSION"
    private const val LIST_MOVIE = "$URL/list/{listId}"
    private const val MOVIE_DETAIL = "$BASE_URL/3/movie/{movieId}"
    private const val SEARCH_MOVIE = "$URL/search/movie"

    private const val CONTENT_TYPE = "Content-Type"

    private const val JSON_TYPE = "application/json"

    private const val QUERY_KEY = "api_key"
    private const val QUERY_PAGE = "page"
    private const val QUERY_MOVIE = "query"

    fun getMovieList(listId: Int, page: Int) = AndroidNetworking.get(LIST_MOVIE)
        .addHeaders(CONTENT_TYPE, JSON_TYPE)
        .addPathParameter("listId", listId.toString())
        .addQueryParameter(
            mapOf<String, String?>(
                QUERY_PAGE to page.toString(),
                QUERY_KEY to API_KEY
            )
        ).build()

    fun getMovieDetail(id: Int) = AndroidNetworking.get(MOVIE_DETAIL)
        .addHeaders(CONTENT_TYPE, JSON_TYPE)
        .addPathParameter("movieId", id.toString())
        .addQueryParameter(
            mapOf<String, String?>(
                QUERY_KEY to API_KEY
            )
        ).build()

    fun searchMovie(query: String, page: Int) = AndroidNetworking.get(SEARCH_MOVIE)
        .addHeaders(CONTENT_TYPE, JSON_TYPE)
        .addQueryParameter(
            mapOf<String, String?>(
                QUERY_PAGE to page.toString(),
                QUERY_MOVIE to query,
                QUERY_KEY to API_KEY
            )
        ).build()
}