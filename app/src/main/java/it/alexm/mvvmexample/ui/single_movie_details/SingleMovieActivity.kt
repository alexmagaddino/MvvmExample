package it.alexm.mvvmexample.ui.single_movie_details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import it.alexm.mvvmexample.BuildConfig
import it.alexm.mvvmexample.R
import it.alexm.mvvmexample.data.api.MovieClient
import it.alexm.mvvmexample.data.beans.MovieDetailBean
import it.alexm.mvvmexample.data.setVisible
import kotlinx.android.synthetic.main.activity_single_movie.*
import java.text.NumberFormat
import java.util.*

class SingleMovieActivity : AppCompatActivity() {

    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieDetailRepository: MovieDetailRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_movie)
        val movieId = intent.getIntExtra("id", -1)

        val apiService = MovieClient.getClient()
        movieDetailRepository = MovieDetailRepository(apiService)

        viewModel = getViewModel(movieId)

        viewModel.movieDetail.observe(this, Observer {
            bindUi(it)
        })

        viewModel.movieDetailNetworkState.observe(this, Observer {
            progress_bar.setVisible(it.isLoading())
            txt_error.setVisible(it.isError())
        })
    }

    private fun bindUi(movieDetail: MovieDetailBean?) {
        movie_title.text = movieDetail?.title
        movie_tagline.text = movieDetail?.tagline
        movie_release_date.text = movieDetail?.releaseDate
        movie_rating.text = movieDetail?.voteAverage.toString()
        movie_runtime.text = movieDetail?.runtime.toString()
        movie_overview.text = movieDetail?.overview

        val formatCurrency = NumberFormat.getCurrencyInstance(Locale.ITALY)
        movie_budget.text = formatCurrency.format(movieDetail?.budget)
        movie_revenue.text = formatCurrency.format(movieDetail?.revenue)

        val moviePosterUrl = BuildConfig.POSTER_HOST + movieDetail?.posterPath
        Glide.with(this)
            .load(moviePosterUrl)
            .into(iv_movie_poster)
    }

    private fun getViewModel(movieId: Int): SingleMovieViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SingleMovieViewModel(movieDetailRepository, movieId) as T
            }
        }).get(SingleMovieViewModel::class.java)
    }
}
