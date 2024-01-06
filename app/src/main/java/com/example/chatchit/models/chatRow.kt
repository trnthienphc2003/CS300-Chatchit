package com.example.chatchit.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class chatRow (
    @SerializedName("id")
    val id:Int ?= null,
    @SerializedName("content")
    val content:String ?= null,
    @SerializedName("roomId")
    val roomId:Int ?= null,
    @SerializedName("senderId")
    val senderid:Int ?= null,
    @SerializedName("user")
    val user: Sender ?= null,
    @SerializedName("metadata")
    val metadata:String ?= "test",
)  : Parcelable