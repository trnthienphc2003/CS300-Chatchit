package com.example.chatchit.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.Date
@Parcelize
class Room (
    @SerializedName("ID")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("CreatedAt")
    val createdAt: Date? = null,
    @SerializedName("UpdatedAt")
    val updatedAt: Date? = null,
    @SerializedName("DeletedAt")
    val deletedAt: Date? = null
) : Parcelable