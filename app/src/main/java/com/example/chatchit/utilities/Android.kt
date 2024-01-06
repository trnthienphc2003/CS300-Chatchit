package com.example.chatchit.utilities

import android.os.Handler
import android.os.Looper
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

private class AndroidContinuation<in T>(val continuation: Continuation<T>) : Continuation<T> by continuation {
    fun resume(value: T) {
        postOnMainThread { continuation.resume(value) }
    }

    fun resumeWithException(exception: Throwable) {
        postOnMainThread { continuation.resumeWithException(exception) }
    }

    private inline fun postOnMainThread(crossinline expr: () -> Unit) {
        if (Looper.myLooper() == Looper.getMainLooper()) expr()
        else Handler(Looper.getMainLooper()).post { expr() }
    }
}

