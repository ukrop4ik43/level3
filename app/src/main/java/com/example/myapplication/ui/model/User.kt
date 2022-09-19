package com.example.myapplication.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var id: Long,
    val photo: String,
    val name: String,
    val occupy: String,
    val homeAddress: String
) : Parcelable