package com.example.chatchit.api

import com.example.chatchit.models.data.LoginDataModel
import com.example.chatchit.models.data.SignupDataModel
import com.example.chatchit.models.response.StatusResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UsersApi {
    @POST("api/v1/auth/login")
    fun userLogin(
        @Body loginDataModel: LoginDataModel
    ): Call<StatusResponse>

    @POST("api/v1/auth/register")
    fun userSignup(
        @Body signupDataModel: SignupDataModel
    ): Call<StatusResponse>
}