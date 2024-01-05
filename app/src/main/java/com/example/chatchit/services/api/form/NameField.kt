package com.example.chatchit.services.api.form

import com.google.gson.annotations.SerializedName

data class NameField(
    @SerializedName("name")
    var data: String = ""
)
