package com.example.roomflowpractice.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ContactEntity::class], version = 1, exportSchema = false)
abstract class ContactsDB : RoomDatabase() {
    abstract fun contactsDao(): ContactsDao
}