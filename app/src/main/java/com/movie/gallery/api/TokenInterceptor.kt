package com.movie.gallery.api

import android.content.Context
import com.movie.gallery.util.UtilityClass
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class TokenInterceptor:Interceptor
{
    override fun intercept(chain: Interceptor.Chain): Response {
        val authTokenApiKey=UtilityClass.TMDB_ACCESS_TOKEN_API_KEY

        val request:Request=chain
            .request()
            .newBuilder()
            .addHeader("accept","application/json")
            .addHeader("Authorization","Bearer $authTokenApiKey")
            .build()

        return chain.proceed(request)
    }
}