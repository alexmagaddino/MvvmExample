package it.alexm.mvvmexample.single_movie_details.data.api

import io.reactivex.Single
import it.alexm.mvvmexample.single_movie_details.data.beans.MovieDetailBean
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDbInterface {

    // {{movie_host}}/popular?api_key={{api_key}}
    //{{movie_host}}/{{movie_id}}?api_key={{api_key}}

    @GET("/movie/popular")
    fun getPopularMovie(): Single<MovieDetailBean>

    @GET("/movie/{movie_id}")
    fun getPopularMovie(@Path("movie_id") id: Int)
}