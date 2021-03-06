package com.github.ramannada.movie.moviedetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.github.ramannada.movie.BuildConfig
import com.github.ramannada.movie.R
import com.github.ramannada.movie.network.response.Movie
import com.github.ramannada.movie.network.response.MovieDetail
import kotlinx.android.synthetic.main.activity_movie_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * Created by labibmuhajir on 2019-06-25.
 * labibmuhajir@yahoo.com
 */
class MovieDetailActivity : AppCompatActivity() {
    var id: Int? = null

    val movieDetailViewModel: MovieDetailViewModel by viewModel { parametersOf(id) }

    companion object {
        fun intentInstance(context: Context, movie: Movie) = Intent(context, MovieDetailActivity::class.java).apply {
            putExtra(Extra.MOVIE.name, movie)
        }
    }

    enum class Extra {
        MOVIE
    }

    private var movie: MovieDetail? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDefaultDisplayHomeAsUpEnabled(true)
        }

        if (intent.hasExtra(Extra.MOVIE.name)) {
            try {
                movie = MovieDetail.movieToMovieDetail(intent.getParcelableExtra(Extra.MOVIE.name))
            } catch (e: Exception) {
                e.printStackTrace()
            }

            movie?.let { bindMovie(it) }
        }

        movieDetailViewModel.apply {
            movieDetail.observe(this@MovieDetailActivity, Observer { bindMovie(it) })
            message.observe(
                this@MovieDetailActivity,
                Observer {
                    if (!it.isNullOrBlank()) {
                        Toast.makeText(this@MovieDetailActivity, it, Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun bindMovie(movieDetail: MovieDetail) {
        movieDetail.let {
            id = it.id
            title = it.title
            tv_title.text = it.title
            tv_release_date.text = it.releaseDate
            tv_homepage.text = it.homepage
            tv_tagline.text = it.tagline
            tv_synopsis.text = it.overview
            var company = ""
            it.productionCompanies?.forEachIndexed { i, c ->
                if (i == 0) company = c?.name ?: ""
                else company += ", ${c?.name ?: ""}"
            }
            tv_company.text = company

            Glide.with(this).load("${BuildConfig.IMAGE_BASE_URL}w500/${it.posterPath}").centerCrop().into(iv_poster)
        }
    }
}
