package it.alexm.mvvmexample.data.api

import io.reactivex.Single
import it.alexm.mvvmexample.data.beans.MovieDetailBean
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDbInterface {

    // {{movie_host}}/popular?api_key={{api_key}}
    //{{movie_host}}/{{movie_id}}?api_key={{api_key}}

    @GET("/movie/popular")
    fun getPopularMovie(): Single<List<MovieDetailBean>>

    @GET("/movie/{movie_id}")
    fun getDetailMovie(@Path("movie_id") id: Int): Single<MovieDetailBean>
}