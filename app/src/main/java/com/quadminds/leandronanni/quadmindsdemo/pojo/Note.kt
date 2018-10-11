package com.quadminds.leandronanni.quadmindsdemo.pojo

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

import java.util.*

@SuppressLint("ParcelCreator")
@Parcelize
class Note (
            val id: String,
            val title: String,
            val content: String) : Parcelable