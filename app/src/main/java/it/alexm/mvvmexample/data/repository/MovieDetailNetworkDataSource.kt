package it.alexm.mvvmexample.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import it.alexm.mvvmexample.data.api.MovieDbInterface
import it.alexm.mvvmexample.data.beans.MovieDetailBean


/**
 * Created by alexm on 14/03/2020
 */
class MovieDetailNetworkDataSource(
    private val apiService: MovieDbInterface,
    private val disposable: CompositeDisposable
) {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _downloadMovieDetail = MutableLiveData<MovieDetailBean>()
    val downloadMovieDetail: LiveData<MovieDetailBean>
        get() = _downloadMovieDetail

    fun fetchMovieDetail(id: Int) {

        _networkState.postValue(NetworkState.Loading)

        try {

            disposable.add(
                apiService.getDetailMovie(id)
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        _downloadMovieDetail.postValue(it)
                        _networkState.postValue(NetworkState.Success)
                    }, {
                        it.printStackTrace()
                        _networkState.postValue(NetworkState.Error)
                    })
            )

        } catch (t: Throwable) {
            t.printStackTrace()
            Log.e(MovieDetailNetworkDataSource::class.java.name, "Error Catched")
        }

    }
}