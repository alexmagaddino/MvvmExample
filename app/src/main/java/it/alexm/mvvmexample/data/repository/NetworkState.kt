package it.alexm.mvvmexample.data.repository


/**
 * Created by alexm on 13/03/2020
 */
enum class NetworkState(val message: String) {
    Loading("Loading"),
    Error("Apposto sta minkia"),
    Success("Apposto"),
    EOL("You have reach the end of list");

    fun isLoading() = this == Loading
    fun isError() = this == Error
    fun isSuccess() = this == Success
    fun isEndOfList() = this == EOL
}
