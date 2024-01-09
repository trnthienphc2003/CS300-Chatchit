package com.example.chatchit.models
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.Date
@Parcelize
class User (
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("avatar")
    val avatar: String? = null,
    @SerializedName("language")
    val language: Language? = null,
) : Parcelable