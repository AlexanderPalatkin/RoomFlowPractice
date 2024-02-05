package com.example.roomflowpractice.repository

import com.example.roomflowpractice.db.ContactEntity
import com.example.roomflowpractice.db.ContactsDao
import javax.inject.Inject

class DatabaseRepository @Inject constructor(
    private val contactsDao: ContactsDao
) {
    suspend fun saveContact(entity: ContactEntity) = contactsDao.saveContact(entity)

    fun getAllContacts() = contactsDao.getAllContacts()

    fun deleteAllContacts() = contactsDao.deleteAllContacts()

    fun sortedASC() = contactsDao.sortedASC()

    fun sortedDESC() = contactsDao.sortedDESC()

    fun searchContact(name: String) = contactsDao.searchContact(name)
}