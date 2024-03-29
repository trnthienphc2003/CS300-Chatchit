package com.example.chatchit.services

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIService {
    companion object {
        const val BASE_URL = "http://103.82.39.199:8000"

        fun getApiClient(
            context: Context,
        ): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(createOkHttpClient(context))
                .build()
        }

        fun getCookies(context: Context): MutableSet<String>? {
            val cookiePrefs: SharedPreferences =
                context.getSharedPreferences("Cookies", Context.MODE_PRIVATE)
            return cookiePrefs.getStringSet("Cookies-Set", HashSet())
        }

        fun clearCookies(context: Context) {
            val cookiePrefs: SharedPreferences =
                context.getSharedPreferences("Cookies", Context.MODE_PRIVATE)
            cookiePrefs.edit().clear().apply()
        }

        private fun createOkHttpClient(
            context: Context,
        ): OkHttpClient {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val clientBuilder = OkHttpClient.Builder().addNetworkInterceptor(loggingInterceptor)
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