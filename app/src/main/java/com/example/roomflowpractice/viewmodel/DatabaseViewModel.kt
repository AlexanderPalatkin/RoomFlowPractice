package com.example.roomflowpractice.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomflowpractice.db.ContactEntity
import com.example.roomflowpractice.repository.DatabaseRepository
import com.example.roomflowpractice.utils.DataStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DatabaseViewModel @Inject constructor(
    private val repository: DatabaseRepository
) : ViewModel() {
    private val _contactList = MutableLiveData<DataStatus<List<ContactEntity>>>()
    val contactList: LiveData<DataStatus<List<ContactEntity>>>
        get() = _contactList

    fun saveContact(entity: ContactEntity) =
        viewModelScope.launch {
            repository.saveContact(entity)
        }

    fun getAllContacts() = viewModelScope.launch {
        repository.getAllContacts()
            .catch {
                _contactList.postValue(DataStatus.error(it.message.toString()))
            }
            .collect {
                _contactList.postValue(DataStatus.success(it, it.isEmpty()))
            }
    }

    fun deleteAllContacts() = viewModelScope.launch {
        repository.deleteAllContacts()
    }

    fun sortedASC() = viewModelScope.launch {
        repository.sortedASC()
            .catch {
                _contactList.postValue(DataStatus.error(it.message.toString()))
            }
            .collect {
                _contactList.postValue(DataStatus.success(it, it.isEmpty()))
            }
    }

    fun sortedDESC() = viewModelScope.launch {
        repository.sortedDESC()
            .catch {
                _contactList.postValue(DataStatus.error(it.message.toString()))
            }
            .collect {
                _contactList.postValue(DataStatus.success(it, it.isEmpty()))
            }
    }

    fun searchContact(name: String) = viewModelScope.launch {
        repository.searchContact(name)
            .collect {
                _contactList.postValue(DataStatus.success(it, it.isEmpty()))
            }
    }
}