package com.kirabium.relayance.ui.viewModel

import androidx.lifecycle.ViewModel
import com.kirabium.relayance.domain.repository.CustomerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AddCustomerViewModel @Inject constructor(private val repository: CustomerRepository): ViewModel() {

    private val _name = MutableStateFlow<String>("")
    val name = _name.asStateFlow()


    private val _email = MutableStateFlow<String>("")
    val email = _email.asStateFlow()

    fun saveCustomer(){
        //TODO
    }

    fun onNameChanged(newName: String) {
        _name.value = newName
    }

    fun onEmailChanged(newEmail: String) {
        _email.value = newEmail
    }
}

