package com.example.chatchit.services.api

import com.example.chatchit.services.api.form.LoginForm
import com.example.chatchit.services.api.form.SignupForm
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET

interface RoomAPI {
    @GET("api/v1/auth/api/v1/room/")
    fun listRoom(): Call<APIResponse>
}