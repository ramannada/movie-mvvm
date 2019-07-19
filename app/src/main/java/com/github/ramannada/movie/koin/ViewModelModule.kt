package com.github.ramannada.movie.koin

import com.github.ramannada.movie.movie.MainViewModel
import com.github.ramannada.movie.moviedetail.MovieDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by labibmuhajir on 2019-06-29.
 * labibmuhajir@yahoo.com
 */
object ViewModelModule {
    val viewModelModule = module {
        viewModel { MainViewModel(get(), get()) }

        viewModel { (id: Int) -> MovieDetailViewModel(id, get(), get()) }
    }
}