package com.example.chatchit.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class Conversation(
    @SerializedName("page")
    val page : Int ?= null,
    @SerializedName("limit")
    val limit : Int ?= null,
    @SerializedName("totalPage")
    val totalPage : Int ?= null,
    @SerializedName("totalRows")
    val totalRows : Int ?= null,
    @SerializedName("rows")
    val rows : List<MessageTranslate> ?= null
)  : Parcelable