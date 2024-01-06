package com.example.chatchit.models
import com.google.gson.annotations.SerializedName
import java.util.Date

class User {
    @SerializedName("ID")
    var id: Int? = null
    @SerializedName("name")
    var name: String? = null
    @SerializedName("email")
    var email: String? = null
    @SerializedName("CreatedAt")
    var createdAt: Date? = null
    @SerializedName("UpdatedAt")
    var updatedAt: Date? = null
    @SerializedName("DeletedAt")
    var deletedAt: Date? = null
}