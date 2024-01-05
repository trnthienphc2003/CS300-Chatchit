package com.example.chatchit.services.api

import com.example.chatchit.services.api.form.LoginForm
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MessageAPI {
    @GET("api/v1/message")
    fun getMessage(
        @Query("RoomId") roomId: String,
        @Query("Page") page: Int,
        @Query("Limit") limit: Int
    ): Call<APIResponse>
}