package it.alexm.mvvmexample.data.api

import io.reactivex.Single
import it.alexm.mvvmexample.data.beans.MovieDetailBean
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDbInterface {

    @GET("movie/popular")
    fun getPopularMovie(): Single<List<MovieDetailBean>>

    @GET("movie/{movie_id}")
    fun getDetailMovie(@Path("movie_id") id: Int): Single<MovieDetailBean>
}