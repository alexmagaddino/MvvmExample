package it.alexm.mvvmexample.single_movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import it.alexm.mvvmexample.data.beans.MovieDetailBean
import it.alexm.mvvmexample.data.repository.NetworkState


/**
 * Created by alexm on 14/03/2020
 */
class SingleMovieViewModel(
    private val movieDetailRepository: MovieDetailRepository,
    movieId: Int
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val movieDetail: LiveData<MovieDetailBean> by lazy {
        movieDetailRepository.fetchSingleMovieDetail(compositeDisposable, movieId)
    }

    val movieDetailNetworkState: LiveData<NetworkState> by lazy {
        movieDetailRepository.getMovieDetailNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}