package com.example.myapplication.domain

import com.example.myapplication.ui.model.User

interface ContactsRepository {
    fun getSize():Int
    fun getUsers(): List<User>
    fun addUser(name: String, occupy: String, photo: String,address:String)
    fun deleteUser(userPosition: Int)
    fun getUserName(userPosition: Int)

}