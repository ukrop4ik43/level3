package com.example.myapplication.ui.contactsList


import android.app.Application
import com.example.myapplication.ui.model.UsersService

class App : Application() {
    var UsersService = UsersService()

}