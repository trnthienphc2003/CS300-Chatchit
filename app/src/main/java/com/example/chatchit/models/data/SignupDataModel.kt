package com.example.chatchit.models.data

import com.google.gson.annotations.SerializedName

data class SignupDataModel(
    @SerializedName("name")
    var name: String = "",
    @SerializedName("email")
    var email: String = "",
    @SerializedName("password")
    var password: String = "",
)