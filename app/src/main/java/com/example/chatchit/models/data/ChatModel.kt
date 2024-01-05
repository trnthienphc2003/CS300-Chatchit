package com.example.chatchit.models.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChatModel(

    @field:SerializedName("ID")
    var ID : Int ? = null,

    @field:SerializedName("CreateAt")
    var CreatedAt : String ? = null,

    @field:SerializedName("UpdateAt")
    var UpdatedAt : String ? = null,

    @field:SerializedName("DeleteAt")
    var DeleteAt : String ? = null,

    @field:SerializedName("name")
    var name : String ? = null
) : Parcelable