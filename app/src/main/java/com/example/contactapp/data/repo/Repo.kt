package com.example.contactapp.data.repo

import com.example.contactapp.data.tables.Contact
import com.example.contactapp.data.database.ContactAppDatabase
import kotlinx.coroutines.flow.Flow

class Repo(val database: ContactAppDatabase) {

    suspend fun upsertContact(contact: Contact) {
        database.getContactDao().upsertContact(contact)
    }
    suspend fun deleteContact(contact: Contact) {
        database.getContactDao().deleteContact(contact)
    }
    fun getAllContact(): Flow<List<Contact>> {
        return database.getContactDao().getAllContact()

    }

}