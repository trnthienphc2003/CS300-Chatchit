package com.example.chatchit.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class FriendAdd (
    @SerializedName("isFriendAdded")
    val isFriendAdded : Boolean,
    @SerializedName("user")
    val user: User ?= null
): Parcelable