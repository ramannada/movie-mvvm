package com.github.ramannada.movie.movie

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.ramannada.movie.R
import com.github.ramannada.movie.moviedetail.MovieDetailActivity
import com.github.ramannada.movie.network.response.Movie
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
                    query?.let {
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

    private var query: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter.listener = this


        query.let {
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

        rv_movie.apply {
            adapter = this@MainActivity.adapter
            addOnScrollListener(scrollListener)
        }

        mainViewModel.apply {
            movie.observe(this@MainActivity, Observer {
                adapter.addMovie(it)
            })

            message.observe(this@MainActivity, Observer {
                if (!it.isNullOrBlank()) Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
            })
        }

        srl_movie.setOnRefreshListener {
            srl_movie.isRefreshing = false
            adapter.clearMovie()

            query.let {
                if (it.isNullOrBlank()) {
                    mainViewModel.initMovieList()
                } else {
                    mainViewModel.searchMovie(it, 1)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        (getSystemService(Context.SEARCH_SERVICE) as SearchManager?)?.let {
            (menu?.findItem(R.id.action_search)?.actionView as SearchView?)?.apply {
                setSearchableInfo(it.getSearchableInfo(componentName))
                setQuery(getString(R.string.search), true)
                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        query?.let {
                            if (it.isNotBlank()) {
                                this@MainActivity.mainViewModel.searchMovie(it, 1)
                                adapter.clearMovie()
                            }
                        }
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        this@MainActivity.query = newText
                        return false
                    }

                })
            }
        }
        return true
    }

    override fun onClick(data: Movie?) {
        data?.let { startActivity(MovieDetailActivity.intentInstance(this, it)) }
    }


}