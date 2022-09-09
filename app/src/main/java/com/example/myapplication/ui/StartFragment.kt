package com.example.myapplication.ui

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityDialogBinding
import com.example.myapplication.databinding.FragmentStartBinding
import com.example.myapplication.ui.contactsList.UserActionListener
import com.example.myapplication.ui.contactsList.UsersAdapter
import com.example.myapplication.ui.viewModel.ContactsViewModel
import com.example.myapplication.ui.viewModel.SharedViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [StartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StartFragment : Fragment() {

    private lateinit var chooseImage: String
    private lateinit var binding: FragmentStartBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var dialogBinding:ActivityDialogBinding

    private val usersAdapter: UsersAdapter by lazy {
        UsersAdapter(object : UserActionListener {
            override fun onUserDelete(userPosition: Int) {
                contactsViewModel.deleteUser(userPosition)
            }
        },sharedViewModel)
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

        binding.addContactB.setOnClickListener{
            dialogCreater(inflater)
        }
        return binding.root
    }

    private fun dialogCreater(inflater: LayoutInflater) {
        val dialog = Dialog(inflater.context)
        dialogBinding = ActivityDialogBinding.inflate(inflater)
        dialog.setCancelable(false)
        dialog.setContentView(dialogBinding.root)
        setDialogListeners(dialog)
        dialog.show()
    }

    private fun setDialogListeners(dialog: Dialog) {
        dialogBinding.arrowBackButton.setOnClickListener {
            dialog.dismiss()
        }
        dialogBinding.saveButton.setOnClickListener {
            saveButtonListener(dialog)
        }
        dialogBinding.addPhotoImageButton.setOnClickListener {
            generateRandomImage(dialogBinding.AvatarImageViewInDialog)
        }
    }

    private fun saveButtonListener(dialog: Dialog) {
        if (dialogBinding.editTextPersonName.text.toString() != "" &&
            dialogBinding.editTextCareer.text.toString() != ""
            && dialogBinding.editTextTextPostalAddress.text.toString() != ""
            && ::chooseImage.isInitialized && chooseImage!=""
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
            dialogBinding.editTextPersonName.text.toString(),
            dialogBinding.editTextCareer.text.toString(),
            chooseImage,dialogBinding.editTextTextPostalAddress.text.toString()
        )
        chooseImage=""
        dialog.dismiss()
    }
    private fun generateRandomImage(imageView: ImageView) {
        val images = arrayOf(
            "https://maximum.fm/uploads/media_news/2020/05/5ec25857a251b581364253.png?w=1200&h=675&il&q=80&output=jpg",
            "https://mir-s3-cdn-cf.behance.net/project_modules/disp/ea7a3c32163929.567197ac70bda.png",
            "https://img.freepik.com/premium-vector/smiling-girl-avatar_102172-32.jpg",
            "https://img.freepik.com/premium-vector/the-face-of-a-cute-girl-avatar-of-a-young-girl-portrait-vector-flat-illustration_192760-82.jpg?w=2000",
            "https://i0.wp.com/www.cssscript.com/wp-content/uploads/2020/12/Customizable-SVG-Avatar-Generator-In-JavaScript-Avataaars.js.png?fit=438%2C408&ssl=1"
        )
        chooseImage = images.random()
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