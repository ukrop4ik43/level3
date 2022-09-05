package com.example.myapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentStartBinding
import com.example.myapplication.ui.contactsList.UserActionListener
import com.example.myapplication.ui.contactsList.UsersAdapter
import com.example.myapplication.ui.model.User
import com.example.myapplication.ui.viewModel.ContactsViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [StartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StartFragment : Fragment() {

    private lateinit var binding: FragmentStartBinding

    private val usersAdapter: UsersAdapter by lazy {
        UsersAdapter(object : UserActionListener {
            override fun onUserDelete(userPosition: Int) {

                contactsViewModel.deleteUser(userPosition)
            }
        })
    }

    private val contactsViewModel: ContactsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartBinding.inflate(inflater, container, false)
//        val view = inflater.inflate(R.layout.fragment_start, container, false)
//        var recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        binding.recyclerView.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = usersAdapter
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
    }

    private fun setObservers() {
        contactsViewModel.contactsListLiveData.observe(viewLifecycleOwner) { users ->
            usersAdapter.submitList(users.toMutableList())
        }
    }
}