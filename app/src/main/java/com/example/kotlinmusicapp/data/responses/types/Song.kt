package com.example.kotlinmusicapp.data.responses.types

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
data class Song (
    val sgn_id: Int,
    val sgn_name: String,
    val sgn_artist : String,
    val sgn_url : String,
    val sgn_img : String,
    val sgn_public : Int,
    val usr_id :Int,
) : Parcelable