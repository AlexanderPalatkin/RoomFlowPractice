package com.example.roomflowpractice.ui.deleteall

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.roomflowpractice.databinding.FragmentDeleteAllContactsBinding
import com.example.roomflowpractice.viewmodel.DatabaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteAllContactsFragment : DialogFragment() {

    private lateinit var binding: FragmentDeleteAllContactsBinding

    private val viewModel: DatabaseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDeleteAllContactsBinding.inflate(inflater, container, false)
        dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnNegative.setOnClickListener {
                dismiss()
            }
            btnPositive.setOnClickListener {
                viewModel.deleteAllContacts()
                dismiss()
            }
        }
    }
}