package com.example.chatchit.services.api.form
import com.google.gson.annotations.SerializedName

data class MessageForm(
    @SerializedName("content")
    var content : String,
    @SerializedName("roomId")
    var roomId : Int,
    @SerializedName("metadata")
    var metadata : String = "test"
)

