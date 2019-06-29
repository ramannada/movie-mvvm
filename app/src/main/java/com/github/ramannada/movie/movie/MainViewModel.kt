package com.github.ramannada.movie.movie

import androidx.lifecycle.MutableLiveData
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.github.ramannada.movie.base.BaseViewModel
import com.github.ramannada.movie.network.ApiService
import com.github.ramannada.movie.network.response.Movie
import com.github.ramannada.movie.network.response.MovieListResponse
import com.github.ramannada.movie.network.response.MovieSearchResponse

/**
 * Created by labibmuhajir on 2019-06-29.
 * labibmuhajir@yahoo.com
 */
class MainViewModel(apiService: ApiService) : BaseViewModel(apiService), ParsedRequestListener<Any?> {
    var movie = MutableLiveData<List<Movie?>>()
    var page = 1
    var listId = 1

    init {
        initMovieList()
    }

    fun initMovieList() {
        message.value = ""
        page = 1
        listId = 1
        getMovieList()
    }

    fun getMovieList() {
        apiService.getMovieList(listId, page)
            .getAsObject(MovieListResponse::class.java, this)
    }

    fun searchMovie(query: String, page: Int? = null) {
        page?.let { this.page = it }
        apiService.searchMovie(query, this.page)
            .getAsObject(MovieSearchResponse::class.java, this)
    }

    override fun onResponse(response: Any?) {
        when (response) {
            is MovieListResponse -> {
                movie.value = response.results
                if (page < response.totalPages ?: this.page && !response.results.isNullOrEmpty()) page++
                else if (page == response.totalPages) {
                    listId++
                    page = 1
                }
            }

            is MovieSearchResponse -> {
                movie.value = response.results
                if (page <= response.totalPages ?: this.page && !response.results.isNullOrEmpty()) page++
            }
        }
    }

    override fun onError(anError: ANError?) {
        message.value = anError?.message
    }
}