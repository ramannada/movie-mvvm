package com.github.ramannada.movie.movie

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.ramannada.movie.BuildConfig
import com.github.ramannada.movie.R
import com.github.ramannada.movie.network.response.Movie

/**
 * Created by labibmuhajir on 2019-06-25.
 * labibmuhajir@yahoo.com
 */
class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieHolder>() {
    private var data: MutableSet<Movie?> = mutableSetOf()
    private var context: Context? = null
    var listener: MovieClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        context = parent.context

        return MovieHolder(LayoutInflater.from(context).inflate(R.layout.holder_movie, parent, false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        try {
            holder.bind(data.elementAt(position))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun addMovie(movies: List<Movie?>) {
        data.addAll(movies)
        notifyDataSetChanged()
    }

    fun clearMovie() {
        data.clear()
        notifyDataSetChanged()
    }

    inner class MovieHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val ivPoster: ImageView? = view.findViewById(R.id.iv_poster)
        private val tvTitle: TextView? = view.findViewById(R.id.tv_title)
        private val tvReleaseDate: TextView? = view.findViewById(R.id.tv_release_date)

        fun bind(data: Movie?) {
            data?.let {
                tvTitle?.text = it.title
                ivPoster?.let { it1 ->
                    Glide.with(itemView)
                        .load("${BuildConfig.IMAGE_BASE_URL}w300/${it.posterPath}")
                        .into(it1)
                }

                itemView.setOnClickListener { listener?.onClick(data) }
            }
        }
    }

    interface MovieClickListener {
        fun onClick(data: Movie?)
    }
}