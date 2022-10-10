package com.example.myapplication.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.example.myapplication.R
import com.example.myapplication.constants.Constants.TAG
import com.example.myapplication.databinding.ActivityDialogBinding

class DialogFragment : DialogFragment() {
    private var _binding: ActivityDialogBinding? = null

    @SuppressLint("InflateParams", "UseGetLayoutInflater")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = ActivityDialogBinding.inflate(LayoutInflater.from(context))
        DialogInterface.OnClickListener { _, which ->
            parentFragmentManager.setFragmentResult(
                "$TAG:defaultRequestKey",
                bundleOf("RESPONSE" to which)
            )
        }
        layoutInflater.inflate(R.layout.activity_dialog, null)
        return AlertDialog.Builder(requireContext())
            .setCancelable(true)
            .create()
    }

}