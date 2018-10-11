package com.quadminds.leandronanni.quadmindsdemo.pojo

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
class Notes (val data: MutableList<Note>) : Parcelable