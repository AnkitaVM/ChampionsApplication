package com.example.championsapplication.data.model
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChampionImage(
    val full: String,
    val sprite: String,
    val group: String
) : Parcelable