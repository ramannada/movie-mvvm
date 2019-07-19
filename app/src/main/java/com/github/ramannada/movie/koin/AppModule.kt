package com.github.ramannada.movie.koin

import com.androidnetworking.gsonparserfactory.GsonParserFactory
import com.github.ramannada.movie.BuildConfig
import com.github.ramannada.movie.network.ApiService
import com.github.ramannada.movie.repository.ResourceRepository
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module

/**
 * Created by labibmuhajir on 2019-06-29.
 * labibmuhajir@yahoo.com
 */
object AppModule {
    val networkModule = module {
        //gson
        single { GsonBuilder().create() }

        //gson parser
        single { GsonParserFactory(get()) }

        //okhttp
        single {
            OkHttpClient.Builder().addNetworkInterceptor(
                HttpLoggingInterceptor().apply {
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                    else HttpLoggingInterceptor.Level.NONE
                }
            ).build()
        }

        // api service
        single { ApiService }

        single { ResourceRepository(get()) }
    }
}