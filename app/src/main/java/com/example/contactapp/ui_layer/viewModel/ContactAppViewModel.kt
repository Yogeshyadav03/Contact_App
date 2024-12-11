package com.example.contactapp.ui_layer.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactapp.data.repo.Repo
import com.example.contactapp.data.tables.Contact
import com.example.contactapp.ui_layer.state.ContactState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ContactAppViewModel @Inject constructor(
    private val repo: Repo
) : ViewModel() {

    val contactList =

        repo.getAllContact()
            .stateIn(
                scope = viewModelScope,
                initialValue = emptyList(),
                started = SharingStarted.WhileSubscribed()
            )

    private val _state = MutableStateFlow(ContactState())
    val state = combine(_state, contactList) { state, contactList ->
        Log.d("TAG", "$state ")
        state.copy(
            contactList = contactList
        )


    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ContactState())


    fun upsertContact() {
        viewModelScope.launch {
            repo.upsertContact(
                Contact(
                    id = state.value.id.value,
                    name = state.value.name.value,
                    phoneNumber = state.value.phoneNumber.value,
                    email = state.value.email.value,
                    dob = state.value.dob.value,
                    image = state.value.image.value
                )



            )


        }



    }



    fun deleteContact() {
        val contact = Contact(
            id = state.value.id.value,
            name = state.value.name.value,
            phoneNumber = state.value.phoneNumber.value,
            email = state.value.email.value,
            dob = state.value.dob.value,
            image = state.value.image.value
        )
        viewModelScope.launch {
            repo.deleteContact(contact)
        }
    }

    fun clearState() {
       _state.value = ContactState(
           name = mutableStateOf(""),
           phoneNumber = mutableStateOf(""),
           email = mutableStateOf(""),
           image = mutableStateOf(null),
       )
    }




}