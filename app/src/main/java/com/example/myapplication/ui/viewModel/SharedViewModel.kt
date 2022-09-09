package com.example.myapplication.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private var _userName = MutableLiveData("Name")
    val userName: LiveData<String> = _userName

    fun saveUserName(newUserName:String){
        _userName.value=newUserName
    }
}