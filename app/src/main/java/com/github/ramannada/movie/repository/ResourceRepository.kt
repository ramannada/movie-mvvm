package com.github.ramannada.movie.repository

import android.content.Context

/**
 * Created by labibmuhajir on 2019-07-19.
 * labibmuhajir@yahoo.com
 */
class ResourceRepository(private val context: Context) {
    fun getString(id: Int): String? = context.getString(id)

    fun getString(id: Int, arg: String): String? = context.getString(id, arg)
}