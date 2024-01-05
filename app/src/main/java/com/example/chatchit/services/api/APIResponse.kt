package com.example.chatchit.services.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class APIResponse(
    @SerializedName("success")
    val success: String ? = null,
    @SerializedName("message")
    val message: String ? = null,
    @SerializedName("statusCode")
    val statusCode: Int ? = null,
    @SerializedName("data")
    val data: @RawValue Any? = null,
) : Parcelable
