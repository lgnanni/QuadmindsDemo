package com.quadminds.leandronanni.quadmindsdemo.pojo

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
class NotePost (@SerializedName("title") val title: String,
                @SerializedName("content") val content: String) : Parcelable