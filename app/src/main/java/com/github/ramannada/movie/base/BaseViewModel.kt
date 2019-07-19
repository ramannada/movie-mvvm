package com.github.ramannada.movie.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.ramannada.movie.network.ApiService
import com.github.ramannada.movie.repository.ResourceRepository

/**
 * Created by labibmuhajir on 2019-06-29.
 * labibmuhajir@yahoo.com
 */
open class BaseViewModel(
    protected val apiService: ApiService,
    protected val resourceRepository: ResourceRepository
) : ViewModel() {
    var message = MutableLiveData<String>()
}