package com.example.chatchit.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.Date
@Parcelize
class Room (
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("avatar")
    val avatar: String? = null,
    @SerializedName("createdAt")
    val createdAt: Date? = null,
    @SerializedName("lastMessage")
    val lastMessage: Message? = null,
) : Parcelable