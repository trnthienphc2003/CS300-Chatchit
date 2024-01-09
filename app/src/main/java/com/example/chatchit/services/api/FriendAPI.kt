package com.example.chatchit.services.api

import com.example.chatchit.services.api.form.EmailField
import com.example.chatchit.services.api.form.FriendIdField
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FriendAPI {
    @GET("api/v1/friend")
    fun getFriends(): Call<APIResponse>

    @POST("api/v1/friend")
    fun addFriend(
        @Body friendIdField: FriendIdField
    ): Call<APIResponse>

    @DELETE("api/v1/friend/{id}")
    fun deleteFriend(
        @Path("id") friendId: Int
    ): Call<APIResponse>

    @POST("api/v1/friend/find")
    fun findFriend(
        @Body email: EmailField
    ): Call<APIResponse>
}