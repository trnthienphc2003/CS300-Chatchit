package com.example.chatchit.models

import com.google.gson.annotations.SerializedName
import java.util.Date
class Room {
    @SerializedName("ID")
    var id: String? = null
    @SerializedName("name")
    var name: String? = null
    @SerializedName("CreatedAt")
    var createdAt: Date? = null
    @SerializedName("UpdatedAt")
    var updatedAt: Date? = null
    @SerializedName("DeletedAt")
    var deletedAt: Date? = null
}