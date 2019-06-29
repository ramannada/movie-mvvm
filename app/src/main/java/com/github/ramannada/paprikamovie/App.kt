package com.github.ramannada.paprikamovie

import android.app.Application
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.gsonparserfactory.GsonParserFactory
import com.github.ramannada.paprikamovie.koin.AppModule
import com.github.ramannada.paprikamovie.koin.ViewModelModule
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * Created by labibmuhajir on 2019-06-29.
 * labibmuhajir@yahoo.com
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.DEBUG else Level.INFO)
            androidContext(this@App)
            modules(
                listOf(
                    AppModule.networkModule,
                    ViewModelModule.viewModelModule
                )
            )
        }

        AndroidNetworking.initialize(this, get())
        AndroidNetworking.setParserFactory(get<GsonParserFactory>())
    }
}