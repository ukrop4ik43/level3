package com.example.myapplication.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentItemDetailBinding
import com.example.myapplication.extensions.addImage
import com.example.myapplication.ui.model.User


/**
 * A simple [Fragment] subclass.
 * Use the [ItemDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ItemDetailFragment : Fragment() {
    private lateinit var binding: FragmentItemDetailBinding
    val args: ItemDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        val userToUpdate = args.contact
        setViewsInFragment(userToUpdate)
        setListeners()
        return binding.root
    }

    private fun setListeners() {
        binding.btnArrowBack.setOnClickListener {
            val action = ItemDetailFragmentDirections.actionItemDetailFragmentToStartFragment()
            findNavController().navigate(action)
        }
    }


    private fun setViewsInFragment(userToUpdate: User) {
        binding.tvDetailUserName.text = userToUpdate.name
        binding.tvDetailCareer.text = userToUpdate.occupy
        binding.tvDetailHomeAddress.text = userToUpdate.homeAddress
        binding.ivDetailAvatar.addImage(userToUpdate)
    }
}