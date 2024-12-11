package com.example.contactapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.contactapp.data.tables.Contact
import com.example.contactapp.data.tables.ContactDao


@Database(entities = arrayOf(Contact::class), version = 2, exportSchema = false)
 abstract class ContactAppDatabase: RoomDatabase() {
     abstract fun getContactDao(): ContactDao
}