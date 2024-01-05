package com.example.chatchit.models.response

import android.os.Parcelable
import com.example.chatchit.component.data.Chat
import com.example.chatchit.models.data.ChatModel
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListRoom (

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("statusCode")
    val statusCode: Int? = null,

    @field:SerializedName("data")
    val data: List<ChatModel>? = null,

) : Parcelable
