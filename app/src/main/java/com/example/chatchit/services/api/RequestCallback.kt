package com.example.chatchit.services.api

import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

interface RequestCallback<T> {
    fun onResponse(response: T)
    fun onFailure(throwable: Throwable? = null)
}
suspend fun <T> Call<T>.await(): T {
    return suspendCoroutine { continuation ->
        enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>?, t: Throwable) {
                continuation.resumeWithException(t)
            }
            override fun onResponse(call: Call<T>?, response: Response<T>) {
                if (response.isSuccessful && response.body() != null) {
                    continuation.resume(response.body() as T)
                } else {
                    continuation.resumeWithException(HttpException(response))
                }
            }
        })
    }
}