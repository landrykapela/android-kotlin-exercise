package com.movie.gallery.api

import com.movie.gallery.util.UtilityClass
import de.hdodenhof.circleimageview.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiInstance {

    //Method to build out the api generic type for the purpose of reusability
    fun <Api> buildApi(api: Class<Api>, tokenInterceptor: TokenInterceptor): Api {
        return Retrofit.Builder().baseUrl(UtilityClass.TMDB_BASE_URL)
            .client(getApiClient(tokenInterceptor))
            .addConverterFactory(GsonConverterFactory.create()).build().create(api)
    }

    //Method to create the client for intercepting the request and logging
    fun getApiClient(tokenInterceptor: TokenInterceptor):OkHttpClient{

        return OkHttpClient.Builder().addInterceptor(tokenInterceptor).also {
            if (BuildConfig.DEBUG){
                val logging=HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                it.addInterceptor(logging)
            }
        }.readTimeout(10,TimeUnit.SECONDS)
            .connectTimeout(10,TimeUnit.SECONDS)
            .build()

    }
}