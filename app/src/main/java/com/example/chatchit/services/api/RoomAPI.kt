package com.example.chatchit.services.api

import com.example.chatchit.services.api.form.NameField
import com.example.chatchit.services.api.form.UserIdField
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RoomAPI {
    @GET("api/v1/room")
    fun listRoom(): Call<APIResponse>

    @POST("api/v1/room/create")
    fun createRoom(
        @Body roomNameField: NameField
    ): Call<APIResponse>

    @POST("api/v1/room/{id}/add-member")
    fun addMember(
        @Body userIdField: UserIdField,
        @Path("id") roomId: Int
    ): Call<APIResponse>

    @GET("api/v1/room/{id}/list-members")
    fun listMember(
        @Path("id") roomId: String
    ): Call<APIResponse>
}