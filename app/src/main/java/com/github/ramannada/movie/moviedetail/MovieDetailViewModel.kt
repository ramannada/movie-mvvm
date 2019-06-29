package com.github.ramannada.movie.moviedetail

import androidx.lifecycle.MutableLiveData
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.github.ramannada.movie.base.BaseViewModel
import com.github.ramannada.movie.network.ApiService
import com.github.ramannada.movie.network.response.MovieDetail

/**
 * Created by labibmuhajir on 2019-06-29.
 * labibmuhajir@yahoo.com
 */
class MovieDetailViewModel(val id: Int, apiService: ApiService) : BaseViewModel(apiService) {
    val movieDetail = MutableLiveData<MovieDetail>()

    init {
        initMovie()
    }

    fun initMovie() {
        message.value = ""
        getMovie()
    }

    fun getMovie() {
        apiService.getMovieDetail(id)
            .getAsObject(MovieDetail::class.java, object : ParsedRequestListener<MovieDetail?> {
                override fun onResponse(response: MovieDetail?) {
                    movieDetail.value = response
                }

                override fun onError(anError: ANError?) {
                    message.value = anError?.message
                }

            })
    }

}