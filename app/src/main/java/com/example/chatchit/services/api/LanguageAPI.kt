package com.example.chatchit.services.api


import com.example.chatchit.services.api.form.LanguageIdField
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface LanguageAPI {
    @GET("api/v1/user/language")
    fun getUserLanguage(): Call<APIResponse>

    @GET("api/v1/language")
    fun getLanguages(): Call<APIResponse>

    @PUT("api/v1/user/language")
    fun updateUserLanguage(
        @Body languageIdField: LanguageIdField
    ): Call<APIResponse>
}