package it.alexm.mvvmexample.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import io.reactivex.disposables.CompositeDisposable
import it.alexm.mvvmexample.data.api.MovieDbInterface
import it.alexm.mvvmexample.data.beans.MovieResult


class MovieDataSourceFactory(
    private val apiService: MovieDbInterface,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, MovieResult>() {

    val movieLiveDataSource = MutableLiveData<MovieListDataSource>()

    override fun create(): DataSource<Int, MovieResult> {
        val movieDataSource = MovieListDataSource(apiService, compositeDisposable)
        movieLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }
}