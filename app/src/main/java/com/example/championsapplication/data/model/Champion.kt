package com.example.championsapplication.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Champion(
    val id: String,
    val key: String,
    val name: String,
    val title: String,
    val blurb: String,
    @SerializedName("image")
    val championImage: ChampionImage?
) : Parcelable