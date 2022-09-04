package com.example.myapplication.model

data class User(
    var id: Long,
    val photo: String,
    val name: String,
    val occupy: String,
    val homeAddress:String
)