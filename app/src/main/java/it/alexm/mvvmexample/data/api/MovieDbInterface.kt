package it.alexm.mvvmexample.data.api

import io.reactivex.Single
import it.alexm.mvvmexample.data.beans.MovieDetailBean
import it.alexm.mvvmexample.data.beans.PopularMovieBean
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDbInterface {

    @GET("movie/popular")
    fun getPopularMovie(@Query("page") page: Int): Single<PopularMovieBean>

    @GET("movie/{movie_id}")
    fun getDetailMovie(@Path("movie_id") id: Int): Single<MovieDetailBean>
}