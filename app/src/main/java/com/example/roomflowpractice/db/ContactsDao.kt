package com.example.roomflowpractice.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.roomflowpractice.utils.Constants.CONTACTS_TABLE
import kotlinx.coroutines.flow.Flow


@Dao
interface ContactsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveContact(contactEntity: ContactEntity)

    @Query("SELECT * FROM $CONTACTS_TABLE")
    fun getAllContacts(): Flow<MutableList<ContactEntity>>

    @Query("DELETE FROM $CONTACTS_TABLE")
    fun deleteAllContacts()

    @Query("SELECT * FROM $CONTACTS_TABLE ORDER BY name ASC")
    fun sortedASC(): Flow<MutableList<ContactEntity>>

    @Query("SELECT * FROM $CONTACTS_TABLE ORDER BY name DESC")
    fun sortedDESC(): Flow<MutableList<ContactEntity>>

    @Query("SELECT * FROM $CONTACTS_TABLE WHERE name LIKE '%' || :name || '%'")
    fun searchContact(name: String): Flow<MutableList<ContactEntity>>
}