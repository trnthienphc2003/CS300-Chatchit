package com.example.chatchit.services.api.form

import com.google.gson.annotations.SerializedName

data class UserIdField(
    @SerializedName("userId")
    var data: Int = -1
)
