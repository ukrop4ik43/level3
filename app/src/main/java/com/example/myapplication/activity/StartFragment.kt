package com.example.myapplication.activity

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myapplication.constants.Constants.imagesArray
import com.example.myapplication.databinding.ActivityDialogBinding
import com.example.myapplication.databinding.FragmentStartBinding
import com.example.myapplication.ui.contactsList.OnContactClickListener
import com.example.myapplication.ui.contactsList.UsersAdapter
import com.example.myapplication.ui.model.User
import com.example.myapplication.ui.viewModel.ContactsViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [StartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StartFragment : Fragment(), OnContactClickListener {

    private lateinit var chooseImage: String
    private lateinit var binding: FragmentStartBinding
    private lateinit var dialogBinding: ActivityDialogBinding

    private val usersAdapter: UsersAdapter by lazy {
        UsersAdapter(onContactClickListener = this)
    }

    private val contactsViewModel: ContactsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartBinding.inflate(inflater, container, false)


        binding.recyclerView.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = usersAdapter
        }

        binding.btnAddContact.setOnClickListener {
            dialogCreate(inflater)
        }
        return binding.root
    }

    override fun onUserDelete(userPosition: Int) {
        contactsViewModel.deleteUser(userPosition)
    }

    //navigateToDetails
    override fun navigateToDetailFragment(user: User) {
        val action = StartFragmentDirections.actionStartFragmentToItemDetailFragment(user)
        findNavController().navigate(action)
    }

    private fun dialogCreate(inflater: LayoutInflater) {
        val dialog = Dialog(inflater.context)
        dialogBinding = ActivityDialogBinding.inflate(inflater)
        dialog.setCancelable(false)
        dialog.setContentView(dialogBinding.root)
        setDialogListeners(dialog)
        dialog.show()
    }

    private fun setDialogListeners(dialog: Dialog) {
        dialogBinding.btnArrowBack.setOnClickListener {
            dialog.dismiss()
        }
        dialogBinding.btnSave.setOnClickListener {
            saveButtonListener(dialog)
        }
        dialogBinding.ibAddPhoto.setOnClickListener {
            generateRandomImage(dialogBinding.ivaAvatarInDialog)
        }
    }

    private fun saveButtonListener(dialog: Dialog) {
        if (dialogBinding.etPersonName.text.toString() != "" &&
            dialogBinding.etOccupy.text.toString() != ""
            && dialogBinding.etAddress.text.toString() != ""
            && ::chooseImage.isInitialized && chooseImage != ""
        ) {
            saveButtonAction(dialog)
        } else {
            Toast.makeText(
                dialog.context,
                "Please input all the data and choose avatar",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun saveButtonAction(dialog: Dialog) {
        contactsViewModel.addUser(
            dialogBinding.etPersonName.text.toString(),
            dialogBinding.etOccupy.text.toString(),
            chooseImage, dialogBinding.etAddress.text.toString()
        )
        chooseImage = ""
        dialog.dismiss()
    }

    private fun generateRandomImage(imageView: ImageView) {
        chooseImage = imagesArray.random()
        Glide.with(this).load(chooseImage).into(imageView)
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