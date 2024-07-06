package com.movie.gallery.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_ETHERNET
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import com.movie.gallery.R
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

class UtilityClass {

    companion object {
        const val TMDB_BASE_URL = "https://api.themoviedb.org/3/"
        const val TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500/"
        const val TMDB_ACCESS_TOKEN_API_KEY =
            "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyN2FmZGYyNWFlOWQyYTA5ZWEzNzQ4YjE0NjJhYjEyYSIsIm5iZiI6MTcyMDE4NzA3OS4zMDAwOTMsInN1YiI6IjY2ODdmNzU1NzkxYzIyNzcyNDkzNzA2YyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Du9GAg4wJVWCB3pRk8sg8CVlLQkA-UGlPb1-13rqUeY"
        const val TMDB_API_KEY = "27afdf25ae9d2a09ea3748b1462ab12a"


        //method to check internet connection
        fun isConnectedToInternet(context: Context): Boolean {
            val connectivityManager = context.getSystemService(
                Context.CONNECTIVITY_SERVICE
            ) as ConnectivityManager

            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }


        //Method to convert the int budget to well formatted currency type
        fun getMovieBudget(budget: String): String {
            return when (budget.length) {

                6 -> {
                    budget[0].toString() +
                            budget[1] +
                            budget[2] + "K"
                }

                7 -> {
                    budget[0].toString() + "." + budget[1] + "M"
                }

                8 -> {
                    budget[0].toString() +
                            budget[1] + "." +
                            budget[2] + "M"
                }

                9 -> {
                    budget[0].toString() +
                            budget[1] +
                            budget[2] + "." +
                            budget[3] + "M"
                }

                10 -> {
                    budget[0].toString() + "." + budget[1] + "B"
                }

                else -> {
                    budget
                }

            }
        }


        /** function which return response
         *  according to response code
         *  returned from api **/
        fun <T> getResponseData(context: Context, response: Response<T>): ResponseResource<T> {
            return when {
                response.code() == 200 -> {
                    ResponseResource.Success(response.body())
                }

                response.code() == 401 -> {

                    ResponseResource.Error(context.getString(R.string.invalid_api_key))

                }

                response.code() == 500 -> {

                    ResponseResource.Error(context.getString(R.string.tatizo))

                }

                response.code() == 504 -> {
                    ResponseResource.Error(context.getString(R.string.timeout_gateway))
                }

                else -> {
                    ResponseResource.Error(context.getString(R.string.tatizo))
                }
            }
        }

        /** function which return
         * throwable response
         *  from
         *  returned api **/
        fun <T> getThrowableError(context: Context, throwable: Throwable): ResponseResource<T> {
            return when (throwable) {
                is SocketTimeoutException -> {
                    ResponseResource.Error(context.getString(R.string.timeout_gateway))
                }

                is IOException -> {
                    ResponseResource.Error(context.getString(R.string.wrong_data))
                }

                is HttpException -> {

                    ResponseResource.Error("Network connection problem")
                }

                else -> {
                    ResponseResource.Error("Network problem")
                }
            }
        }
    }
}