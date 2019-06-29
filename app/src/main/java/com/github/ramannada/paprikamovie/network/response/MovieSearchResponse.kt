package com.github.ramannada.paprikamovie.network.response

import com.google.gson.annotations.SerializedName

/**
 * Created by labibmuhajir on 2019-06-29.
 * labibmuhajir@yahoo.com
 */

data class MovieSearchResponse(
    val page: Int?,
    val results: List<Movie?>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)