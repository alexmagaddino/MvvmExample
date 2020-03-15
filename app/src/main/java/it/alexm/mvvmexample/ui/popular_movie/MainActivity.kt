package it.alexm.mvvmexample.ui.popular_movie

import android.os.Bundle
import android.widget.GridLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import it.alexm.mvvmexample.R
import it.alexm.mvvmexample.data.api.MovieClient
import it.alexm.mvvmexample.data.setVisible
import kotlinx.android.synthetic.main.activity_main.*


/**
 * Created by alexm on 13/03/2020
 */
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var pageListRepository: MoviePageListRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiService = MovieClient.getClient()
        pageListRepository = MoviePageListRepository(apiService)

        viewModel = getViewModel()

        val adapter = MovieListAdapter(this)

        val layoutManager = GridLayoutManager(this, 3).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val viewType = adapter.getItemViewType(position)
                    return if (viewType == MovieListAdapter.MOVIE_TYPE) 1
                    else 3
                }
            }
        }

        movie_list.layoutManager = layoutManager
        movie_list.setHasFixedSize(true)
        movie_list.adapter = adapter

        viewModel.pagedList.observe(this, Observer(adapter::submitList))

        viewModel.networkState.observe(this, Observer {
            progress_bar_popular.setVisible(viewModel.listIsEmpty() && it.isLoading())
            txt_error_popular.setVisible(viewModel.listIsEmpty() && it.isError())

            if (!viewModel.listIsEmpty()) {
                adapter.setNetworkState(it)
            }
        })
    }

    private fun getViewModel(): MainViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel(pageListRepository) as T
            }
        }).get(MainViewModel::class.java)
    }
}