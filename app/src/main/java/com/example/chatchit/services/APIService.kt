package com.example.chatchit.services

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.chatchit.models.User
import com.example.chatchit.services.api.APIResponse
import com.example.chatchit.services.api.AuthAPI
import com.example.chatchit.services.api.form.LoginForm
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIService {
    companion object {
        const val BASE_URL = "https://chatchit.1kkfunk.id.vn/"

        fun getApiClient(
            context: Context,
            clearCookie: Boolean = false,
        ): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(createOkHttpClient(context, clearCookie))
                .build()
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