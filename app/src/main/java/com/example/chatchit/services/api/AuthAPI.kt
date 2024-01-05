package com.example.chatchit.services.api

import com.example.chatchit.services.api.form.LoginForm
import com.example.chatchit.services.api.form.SignupForm
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET

interface AuthAPI {
    @POST("api/v1/auth/login")
    fun login(
        @Body loginDataModel: LoginForm
    ): Call<APIResponse>

    @POST("api/v1/auth/register")
    fun signup(
        @Body signupForm: SignupForm
    ): Call<APIResponse>

    @GET("api/v1/auth/user")
    fun getUser(): Call<APIResponse>

    @POST("api/v1/auth/logout")
    fun logout(): Call<APIResponse>
}