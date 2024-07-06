package com.movie.gallery.util

sealed class ResponseResource <T> (val data:T?=null,val message:String?=null){

    class Loading<T>:ResponseResource<T>()
    class Success<T>(data: T?):ResponseResource<T>(data)

    class Error<T>(message: String?,data:T?=null):ResponseResource<T>(data,message)
}