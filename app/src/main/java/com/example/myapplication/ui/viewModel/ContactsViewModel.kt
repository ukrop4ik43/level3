package com.example.myapplication.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.ContactsRepositoryImpl
import com.example.myapplication.domain.ContactsRepository
import com.example.myapplication.ui.model.User

class ContactsViewModel() : ViewModel() {

    private val _contactsListLiveData = MutableLiveData<List<User>>()
    val contactsListLiveData: LiveData<List<User>>
        get() = _contactsListLiveData

    private val contactsRepository: ContactsRepository

    init {
        contactsRepository = ContactsRepositoryImpl()
        val users = getUsers()
        _contactsListLiveData.value = users
    }

    fun getSize(): Int {
        return contactsRepository.getSize()
    }

    private fun getUsers(): List<User> {
        return contactsRepository.getUsers()
    }

    fun addUser(name: String, occupy: String, photo: String, address: String) {
        contactsRepository.addUser(name, occupy, photo, address)
        _contactsListLiveData.value = _contactsListLiveData.value
    }

    fun deleteUser(userPosition: Int) {
        Log.d(TAG, "delete user!")
        contactsRepository.deleteUser(userPosition)
        _contactsListLiveData.value = _contactsListLiveData.value
    }

    companion object {
        private const val TAG = "ContactsViewModel"
    }
}