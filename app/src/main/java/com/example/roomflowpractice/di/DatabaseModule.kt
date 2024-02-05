package com.example.roomflowpractice.di

import android.content.Context
import androidx.room.Room
import com.example.roomflowpractice.db.ContactEntity
import com.example.roomflowpractice.db.ContactsDB
import com.example.roomflowpractice.utils.Constants.CONTACTS_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, ContactsDB::class.java, CONTACTS_DATABASE
    )
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideDao(db: ContactsDB) = db.contactsDao()

    @Provides
    @Singleton
    fun provideEntity() = ContactEntity()
}