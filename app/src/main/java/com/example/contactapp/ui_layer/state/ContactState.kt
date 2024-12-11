package com.example.contactapp.ui_layer.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.contactapp.data.tables.Contact

data class ContactState(
    val contactList: List<Contact> = emptyList(),
    val id: MutableState<Int?> = mutableStateOf(null),
    val phoneNumber: MutableState<String> = mutableStateOf(""),
    val name: MutableState<String> = mutableStateOf(""),
    val email : MutableState<String> = mutableStateOf(""),
    val dob : MutableState<Long?> = mutableStateOf(null),
    val image : MutableState<ByteArray?> = mutableStateOf(null)
)

