package com.example.roomflowpractice.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.roomflowpractice.R
import com.example.roomflowpractice.adapter.ContactsAdapter
import com.example.roomflowpractice.databinding.ActivityMainBinding
import com.example.roomflowpractice.ui.addfragment.AddContactFragment
import com.example.roomflowpractice.ui.deleteall.DeleteAllContactsFragment
import com.example.roomflowpractice.utils.DataStatus
import com.example.roomflowpractice.utils.isVisible
import com.example.roomflowpractice.viewmodel.DatabaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var contactsAdapter: ContactsAdapter

    private val viewModel: DatabaseViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    private var selectedItem = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnShowDialog.setOnClickListener {
                AddContactFragment().show(
                    supportFragmentManager, AddContactFragment().tag
                )
            }

            viewModel.getAllContacts()
            viewModel.contactList.observe(this@MainActivity) {
                when (it.status) {
                    DataStatus.Status.LOADING -> {
                        loading.isVisible(true, rvContacts)
                        emptyBody.isVisible(false, rvContacts)
                    }

                    DataStatus.Status.SUCCESS -> {
                        it.isEmpty?.let { isEmpty ->
                            showEmpty(isEmpty)
                        }
                        loading.isVisible(false, rvContacts)
                        contactsAdapter.differ.submitList(it.data)
                        rvContacts.apply {
                            adapter = contactsAdapter
                        }
                    }

                    DataStatus.Status.ERROR -> {
                        loading.isVisible(false, rvContacts)
                        Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            mainToolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.actionDeleteAll -> {
                        DeleteAllContactsFragment().show(
                            supportFragmentManager,
                            DeleteAllContactsFragment().tag
                        )
                        return@setOnMenuItemClickListener true
                    }

                    R.id.actionSort -> {
                        filter()
                        return@setOnMenuItemClickListener true
                    }

                    R.id.actionSearch -> {
                        return@setOnMenuItemClickListener false
                    }

                    else -> {
                        return@setOnMenuItemClickListener false
                    }
                }
            }
        }
    }

    private fun showEmpty(isShown: Boolean) {
        binding.apply {
            if (isShown) {
                emptyBody.isVisible(true, listBody)
            } else {
                emptyBody.isVisible(false, listBody)
            }
        }
    }

    private fun filter() {
        val builder = AlertDialog.Builder(this)
        val sortItem = arrayOf("Newer(Default)", "Name : A-Z", "Name : Z-A")
        builder.setSingleChoiceItems(sortItem, selectedItem) { dialog, item ->
            when (item) {
                0 -> viewModel.getAllContacts()
                1 -> viewModel.sortedASC()
                2 -> viewModel.sortedDESC()
            }
            selectedItem = item
            dialog.dismiss()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val search = menu.findItem(R.id.actionSearch)
        val searchView = search?.actionView as SearchView
        searchView.queryHint = "Search..."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    viewModel.searchContact(it)
                }
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
}