package com.example.chatchit.services.api.form

import com.google.gson.annotations.SerializedName

data class SignupForm(
    @SerializedName("name")
    var name: String = "",
    @SerializedName("email")
    var email: String = "",
    @SerializedName("password")
    var password: String = "",
)