package com.movie.gallery.di

import android.content.Context
import androidx.room.Room
import com.movie.gallery.api.ApiInstance
import com.movie.gallery.api.MovieApi
import com.movie.gallery.api.TokenInterceptor
import com.movie.gallery.room.MovieDao
import com.movie.gallery.room.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun getRoomDatabaseInstance(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(context, MovieDatabase::class.java, "Movie")
            .fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun getDatabaseDao(movieDatabase: MovieDatabase):MovieDao{
        return movieDatabase.movieDao()
    }

    @Singleton
    @Provides
    fun getApiInstance():ApiInstance{
        return ApiInstance()
    }

    @Singleton
    @Provides
    fun getTokenInterceptor():TokenInterceptor{
        return TokenInterceptor()
    }

    @Singleton
    @Provides
    fun getMovieApiInstance(apiInstance: ApiInstance,tokenInterceptor: TokenInterceptor):MovieApi{
        return apiInstance.buildApi(MovieApi::class.java,tokenInterceptor)
    }


}