package com.github.ramannada.paprikamovie.movie

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.ramannada.paprikamovie.R
import com.github.ramannada.paprikamovie.moviedetail.MovieDetailActivity
import com.github.ramannada.paprikamovie.network.response.Movie
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * Created by labibmuhajir on 2019-06-25.
 * labibmuhajir@yahoo.com
 */
class MainActivity : AppCompatActivity(), MovieAdapter.MovieClickListener {
    private var adapter = MovieAdapter()
    val mainViewModel: MainViewModel by viewModel()
    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            try {
                if ((rv_movie.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition() == this@MainActivity.adapter.itemCount - 1) {
                    set_movie.text.toString().let {
                        if (it.isNullOrBlank()) {
                            mainViewModel.getMovieList()
                        } else {
                            mainViewModel.searchMovie(it)
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter.listener = this

        set_movie.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    set_movie.text.toString().let {
                        if (it.isNullOrBlank()) Toast.makeText(
                            this@MainActivity,
                            getString(R.string.msg_empty_search),
                            Toast.LENGTH_SHORT
                        ).show()
                        else {
                            rv_movie.clearOnScrollListeners()
                            adapter.clearMovie()
                            mainViewModel.searchMovie(it, 1)
                            rv_movie.addOnScrollListener(scrollListener)
                        }
                    }
                    true
                }

                else -> false
            }
        }

        rv_movie.apply {
            adapter = this@MainActivity.adapter
            addOnScrollListener(scrollListener)
        }

        mainViewModel.apply {
            movie.observe(this@MainActivity, Observer {
                adapter.addMovie(it)
                adapter.notifyDataSetChanged()
            })

            message.observe(this@MainActivity, Observer {
                if (!it.isNullOrBlank()) Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
            })
        }

        srl_movie.setOnRefreshListener {
            srl_movie.isRefreshing = false
            adapter.clearMovie()

            set_movie.text.toString().let {
                if (it.isNullOrBlank()) {
                    mainViewModel.initMovieList()
                } else {
                    mainViewModel.searchMovie(it, 1)
                }
            }
        }
    }

    override fun onClick(data: Movie?) {
        data?.let { startActivity(MovieDetailActivity.intentInstance(this, it)) }
    }
}
