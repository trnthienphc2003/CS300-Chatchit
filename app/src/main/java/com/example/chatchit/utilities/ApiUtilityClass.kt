package com.example.chatchit.utilities

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.layout.ColumnScope
import com.example.chatchit.models.response.StatusResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class ApiUtilityClass {
    companion object {
        private fun getBaseUrl(debug: Boolean = false): String {
            if(debug) return "https://chatchit.1kkfunk.id.vn/"
            return "https://chatchit.1kkfunk.id.vn/"
        }

        // source: https://shorturl.at/oABF9
        fun parseError(
            errorBody: ResponseBody?
        ) : StatusResponse {
            val gson = Gson()
            val type = object : TypeToken<StatusResponse>() {}.type
            var errorResponse: StatusResponse? = null
            try {
                errorResponse = gson.fromJson(errorBody!!.charStream(), type)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return errorResponse!!
        }

        fun getApiClient(
            context: Context,
            clearCookie: Boolean = false,
        ): Retrofit {
            return Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .client(createOkHttpClient(context, clearCookie))
                .build()
        }

        fun <T> debug(
            response: retrofit2.Response<T>
        ) {
            println("Oh no, oh no, Error errorBody: ${response.errorBody()}")
            println("Oh no, oh no, Error message: ${response.message()}")
            println("Oh no, oh no, Error code: ${response.code()}")
            println("Oh no, oh no, Error raw: ${response.raw()}")
            println("Oh no, oh no, Error body: ${response.body()}")
            try {
                val error = parseError(response.errorBody())
                println("Oh no, oh no, Error error: $error")
            } catch (e: Exception) {
                println("charStream: ${response.errorBody()?.charStream()}")
                println("Cannot parse Error: ${e.message}")
            }
        }

        private fun createOkHttpClient(
            context: Context,
            clearCookie: Boolean = false,
        ): OkHttpClient {
            val clientBuilder = OkHttpClient.Builder()
            val cookiePrefs: SharedPreferences = context.getSharedPreferences("Cookies", Context.MODE_PRIVATE)
            val cookies = cookiePrefs.getStringSet("Cookies-Set", HashSet())

            val interceptor = Interceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                if (cookies != null) {
                    for (cookie in cookies) {
                        requestBuilder.addHeader("Cookie", cookie)
                    }
                }
                val request = requestBuilder.build()
                val response = chain.proceed(request)
                if (response.headers("Set-Cookie").isNotEmpty()) {
                    val cookies = HashSet<String>()
                    for (header in response.headers("Set-Cookie")) {
                        cookies.add(header)
                    }
                    val editor = cookiePrefs.edit()
                    editor.putStringSet("Cookies-Set", cookies)
                    editor.apply()
                }

                response
            }

            clientBuilder.addInterceptor(interceptor)
            return clientBuilder.build()
        }
    }
}