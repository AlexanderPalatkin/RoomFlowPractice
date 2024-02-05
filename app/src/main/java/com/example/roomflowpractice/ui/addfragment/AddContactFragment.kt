package com.example.roomflowpractice.ui.addfragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.roomflowpractice.databinding.FragmentAddContactBinding
import com.example.roomflowpractice.db.ContactEntity
import com.example.roomflowpractice.viewmodel.DatabaseViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddContactFragment : DialogFragment() {

    @Inject
    lateinit var entity: ContactEntity

    private val viewModel: DatabaseViewModel by viewModels()

    private var contactId = 0
    private var name = ""
    private var phone = ""

    private lateinit var binding: FragmentAddContactBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddContactBinding.inflate(inflater, container, false)
        dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            ivClose.setOnClickListener {
                dismiss()
            }
            btnSave.setOnClickListener {
                name = etName.text.toString()
                phone = etPhone.text.toString()
                if (name.isEmpty() || phone.isEmpty()) {
                    Snackbar.make(it, "Name and Phone cannot be empty", Snackbar.LENGTH_SHORT)
                        .show()
                } else {
                    entity.id = contactId
                    entity.name = name
                    entity.phone = phone

                    viewModel.saveContact(entity)

                    etName.setText("")
                    etPhone.setText("")

                    dismiss()
                }
            }
        }
    }
}