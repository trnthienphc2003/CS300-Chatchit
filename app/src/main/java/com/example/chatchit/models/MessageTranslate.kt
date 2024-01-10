package com.example.chatchit.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
class MessageTranslate (
    @SerializedName("id")
    val id:Int ?= null,
    @SerializedName("createdAt")
    val createdAt: Date?= null,
    @SerializedName("messageId")
    val messageId:Int ?= null,
    @SerializedName("message")
    val message:Message ?= null,
    @SerializedName("languageId")
    val languageId:Int ?= null,
    @SerializedName("language")
    val language:Language ?= null,
    @SerializedName("translatedContent")
    val translatedContent:String ?= null,
) : Parcelable