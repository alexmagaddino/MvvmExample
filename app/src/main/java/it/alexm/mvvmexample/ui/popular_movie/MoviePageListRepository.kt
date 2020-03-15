package it.alexm.mvvmexample.ui.popular_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.reactivex.disposables.CompositeDisposable
import it.alexm.mvvmexample.data.api.MovieClient.MOVIE_PER_PAGE
import it.alexm.mvvmexample.data.api.MovieDbInterface
import it.alexm.mvvmexample.data.beans.MovieResult
import it.alexm.mvvmexample.data.repository.MovieDataSourceFactory
import it.alexm.mvvmexample.data.repository.MovieListDataSource
import it.alexm.mvvmexample.data.repository.NetworkState

class MoviePageListRepository(private val apiService: MovieDbInterface) {

    lateinit var moviePageList: LiveData<PagedList<MovieResult>>
    lateinit var movieDataSource: MovieDataSourceFactory

    fun fetchPagedMovieList(compositeDisposable: CompositeDisposable): LiveData<PagedList<MovieResult>> {
        movieDataSource = MovieDataSourceFactory(apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(MOVIE_PER_PAGE)
            .build()

        moviePageList = LivePagedListBuilder(movieDataSource, config).build()

        return moviePageList
    }

    fun getNetWorkState(): LiveData<NetworkState> {
        return Transformations.switchMap<MovieListDataSource, NetworkState>(
            movieDataSource.movieLiveDataSource, MovieListDataSource::networkState
        )
    }
}