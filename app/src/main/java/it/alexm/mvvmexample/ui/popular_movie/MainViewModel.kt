package it.alexm.mvvmexample.ui.popular_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import io.reactivex.disposables.CompositeDisposable
import it.alexm.mvvmexample.data.beans.MovieResult
import it.alexm.mvvmexample.data.repository.NetworkState

class MainViewModel(private val pagedListRepository: MoviePageListRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val pagedList: LiveData<PagedList<MovieResult>> by lazy {
        pagedListRepository.fetchPagedMovieList(compositeDisposable)
    }

    val networkState: LiveData<NetworkState> by lazy {
        pagedListRepository.getNetWorkState()
    }

    fun listIsEmpty(): Boolean = pagedList.value?.isEmpty() ?: true

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}