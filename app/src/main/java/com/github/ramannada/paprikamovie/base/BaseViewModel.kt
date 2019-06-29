package com.github.ramannada.paprikamovie.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.ramannada.paprikamovie.network.ApiService

/**
 * Created by labibmuhajir on 2019-06-29.
 * labibmuhajir@yahoo.com
 */
open class BaseViewModel(val apiService: ApiService) : ViewModel() {
    var message = MutableLiveData<String>()
}