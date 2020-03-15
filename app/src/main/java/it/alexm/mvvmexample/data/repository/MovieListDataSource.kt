package it.alexm.mvvmexample.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import it.alexm.mvvmexample.data.api.MovieClient.FIRST_PAGE
import it.alexm.mvvmexample.data.api.MovieDbInterface
import it.alexm.mvvmexample.data.beans.MovieResult


/**
 * Created by alexm on 15/03/2020
 */
class MovieListDataSource(
    private val apiService: MovieDbInterface,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, MovieResult>() {

    private var page = FIRST_PAGE
    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MovieResult>
    ) {
        _networkState.postValue(NetworkState.Loading)

        compositeDisposable.add(
            apiService.getPopularMovie(page)
                .subscribeOn(Schedulers.io())
                .subscribe { popularMovie, t ->
                    t?.let {
                        it.printStackTrace()
                        _networkState.postValue(NetworkState.Error)
                        callback.onError(it)
                    } ?: kotlin.run {
                        callback.onResult(
                            popularMovie.results,
                            null, page + 1
                        )
                        _networkState.postValue(NetworkState.Success)
                    }
                }
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieResult>) {
        _networkState.postValue(NetworkState.Loading)

        compositeDisposable.add(
            apiService.getPopularMovie(params.key)
                .subscribeOn(Schedulers.io())
                .subscribe { popularMovie, t ->
                    t?.let {
                        it.printStackTrace()
                        _networkState.postValue(NetworkState.Error)
                        callback.onError(it)
                    } ?: kotlin.run {
                        if (popularMovie.totalPages >= params.key) {
                            callback.onResult(popularMovie.results, params.key + 1)
                            _networkState.postValue(NetworkState.Success)
                        } else {
                            _networkState.postValue(NetworkState.EOL)
                        }
                    }
                }
        )
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, MovieResult>
    ) {

    }
}