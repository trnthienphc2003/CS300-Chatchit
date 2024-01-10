package com.example.chatchit.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
class Message (
    @SerializedName("id")
    val id:Int ?= null,
    @SerializedName("content")
    val content:String ?= null,
    @SerializedName("roomId")
    val roomId:Int ?= null,
    @SerializedName("senderId")
    val senderId:Int ?= null,
    @SerializedName("user")
    val user: User ?= null,
    @SerializedName("metadata")
    val metadata:String ?= "test",
    @SerializedName("createdAt")
    val createdAt: Date ?= null,
)  : Parcelable