package com.example.chatchit.services.api.form

import com.google.gson.annotations.SerializedName

data class EmailField (
    @SerializedName("email")
    var data: String = ""
)