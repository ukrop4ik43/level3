package com.example.myapplication

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.contactsList.App
import com.example.myapplication.contactsList.UserActionListener
import com.example.myapplication.contactsList.UsersAdapter
import com.example.myapplication.model.User
import com.example.myapplication.model.UsersService
import com.example.myapplication.model.usersListener
import com.example.myapplication.R



/**
 * A simple [Fragment] subclass.
 * Use the [startFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class startFragment : Application() {

    private lateinit var adapter: UsersAdapter

    private val usersService: UsersService
        get() = ( as App).UsersService


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_start, container, false)
        var recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL ,false)
        adapter = UsersAdapter(object : UserActionListener {
            override fun onUserDelete(user: User) {
                usersService.deleteUser(user)
            }
        })
        recyclerView.adapter = adapter
        // Inflate the layout for this fragment
        return view
    }


    override fun onDestroy() {
        super.onDestroy()
        usersService.deleteListener(usersListener)
    }

    private val usersListener: usersListener = {
        adapter.users = it
    }

}