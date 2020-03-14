package it.alexm.mvvmexample.single_movie_details

import androidx.lifecycle.LiveData
import io.reactivex.disposables.CompositeDisposable
import it.alexm.mvvmexample.data.api.MovieDbInterface
import it.alexm.mvvmexample.data.beans.MovieDetailBean
import it.alexm.mvvmexample.data.repository.MovieDetailNetworkDataSource
import it.alexm.mvvmexample.data.repository.NetworkState


/**
 * Created by alexm on 14/03/2020
 */
class MovieDetailRepository(private val apiService: MovieDbInterface) {
    lateinit var movieDetailNetworkDataSource: MovieDetailNetworkDataSource

    fun fetchSingleMovieDetail(
        compositeDisposable: CompositeDisposable,
        movieId: Int
    ): LiveData<MovieDetailBean> {
        movieDetailNetworkDataSource = MovieDetailNetworkDataSource(apiService, compositeDisposable)
        movieDetailNetworkDataSource.fetchMovieDetail(movieId)

        return movieDetailNetworkDataSource.downloadMovieDetail
    }

    fun getMovieDetailNetworkState(): LiveData<NetworkState> {
        return movieDetailNetworkDataSource.networkState
    }
}