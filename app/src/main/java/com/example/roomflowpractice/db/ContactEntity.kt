package com.example.roomflowpractice.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.roomflowpractice.utils.Constants.CONTACTS_TABLE

@Entity(tableName = CONTACTS_TABLE)
data class ContactEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String = "",
    var phone: String = ""
)
