package com.example.myapplication.contactsList


import android.app.Application
import com.example.myapplication.model.UsersService

class App : Application() {
    var UsersService = UsersService()

}