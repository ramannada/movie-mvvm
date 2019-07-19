package com.github.ramannada.movie.movie

import androidx.lifecycle.MutableLiveData
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.github.ramannada.movie.R
import com.github.ramannada.movie.base.BaseViewModel
import com.github.ramannada.movie.network.ApiService
import com.github.ramannada.movie.network.response.Movie
import com.github.ramannada.movie.network.response.MovieListResponse
import com.github.ramannada.movie.network.response.MovieSearchResponse
import com.github.ramannada.movie.repository.ResourceRepository

/**
 * Created by labibmuhajir on 2019-06-29.
 * labibmuhajir@yahoo.com
 */
class MainViewModel(apiService: ApiService, resourceRepository: ResourceRepository) :
    BaseViewModel(apiService, resourceRepository), ParsedRequestListener<Any?> {
    var movie = MutableLiveData<MutableSet<Movie?>>()
    var messageBody = MutableLiveData<String>()
    private var page = 1
    private var listId = 1
    private var query = ""

    init {
        initMovieList()
    }

    fun initMovieList() {
        movie.postValue(mutableSetOf())
        message.postValue("")
        messageBody.postValue("")
        query = ""
        page = 1
        listId = 1
        getMovieList()
    }

    fun getMovieList() {
        apiService.getMovieList(listId, page)
            .getAsObject(MovieListResponse::class.java, this)
    }

    fun searchMovie(query: String, page: Int? = null) {
        this.query = query

        page?.let { this.page = it }
        movie.postValue(mutableSetOf())
        apiService.searchMovie(this.query, this.page)
            .getAsObject(MovieSearchResponse::class.java, this)
    }

    override fun onResponse(response: Any?) {
        when (response) {
            is MovieListResponse -> {
                response.results?.let {
                    movie.value = movie.value?.apply {
                        addAll(it)
                    }
                    if (it.isNullOrEmpty()) messageBody.postValue(resourceRepository.getString(R.string.msg_no_data))
                    else messageBody.postValue("")
                }
                if (page < response.totalPages ?: this.page && !response.results.isNullOrEmpty()) page++
                else if (page == response.totalPages) {
                    listId++
                    page = 1
                }
            }

            is MovieSearchResponse -> {
                response.results?.let {
                    movie.value = movie.value?.apply {
                        addAll(it)
                    }
                    if (it.isNullOrEmpty()) messageBody.postValue(
                        resourceRepository.getString(
                            R.string.format_msg_search_not_found,
                            query
                        )
                    ) else messageBody.postValue("")
                }
                if (page < response.totalPages ?: this.page && !response.results.isNullOrEmpty()) page++
            }
        }
    }

    override fun onError(anError: ANError?) {
        message.value = anError?.message
    }
}