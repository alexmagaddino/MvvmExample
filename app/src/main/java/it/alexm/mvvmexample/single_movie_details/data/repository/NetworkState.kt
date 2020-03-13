package it.alexm.mvvmexample.single_movie_details.data.repository


/**
 * Created by alexm on 13/03/2020
 */
sealed class NetworkState(open val message: String)

object Loading : NetworkState("Loading")

class Error(override val message: String) : NetworkState(message)

class Success(override val message: String) : NetworkState(message)
