package com.example.myapplication.data

import com.example.myapplication.domain.ContactsRepository
import com.example.myapplication.ui.model.User

class ContactsRepositoryImpl : ContactsRepository {
    private var users = mutableListOf<User>()

    init {
        users.add(
            0,
            User(
                0,
                "https://klike.net/uploads/posts/2019-03/medium/1551512888_2.jpg",
                "Mirinda",
                "Director",
                "America"
            )
        )
        users.add(
            1,
            User(
                1,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT7jjlSA45DfwCA11HmIjEa4tNvdE2uq8rtHVrfkga2Co4jw3ZoR3IT17zG5m2ZK2cjii4&usqp=CAU",
                "Nirinda",
                "Direct",
                "UK"
            )
        )
        users.add(
            2,
            User(
                2,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ27yNHVDFInW5N1RYi9dt02vbzpYHnne5xIA&usqp=CAU",
                "Miri",
                "Dictor",
                "Ukraine"
            )
        )
        users.add(
            3,
            User(
                3,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/800px-Image_created_with_a_mobile_phone.png",
                "Nil",
                "It",
                "Lapland"
            )
        )


    }

    override fun getSize(): Int {
        return users.size
    }

    override fun getUsers(): List<User> {
        return users
    }


    override fun addUser(name: String, occupy: String, photo: String, address: String) {
        users.add(users.size, User(users.size.toLong(), photo, name, occupy, address))

    }

    override fun deleteUser(userPosition: Int) {
        users.removeAt(userPosition)
    }

    override fun getUserName(userPosition: Int): String {
        return users.get(userPosition).name
    }

    override fun getUserOccupy(userPosition: Int): String {
        return users.get(userPosition).occupy
    }

    override fun getUserHomeAddress(userPosition: Int): String {
        return users.get(userPosition).homeAddress
    }

    override fun getUserPhoto(userPosition: Int): String {
        return users.get(userPosition).photo
    }
}