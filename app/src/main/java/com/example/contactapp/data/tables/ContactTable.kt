package com.example.contactapp.data.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact_table")
data class Contact(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val name: String,
    val phoneNumber: String,
    val email: String,
    val dob : Long?,
    val image : ByteArray?
)


