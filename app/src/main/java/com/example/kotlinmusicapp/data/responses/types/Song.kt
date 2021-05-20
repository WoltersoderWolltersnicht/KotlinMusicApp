package com.example.kotlinmusicapp.data.responses.types

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
data class Song (
    val id: Int,
    val name: String,
) : Parcelable