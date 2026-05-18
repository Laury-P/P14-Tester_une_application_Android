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

    private val _uiState = MutableStateFlow<UiState?>(null)
    val uiState = _uiState.asStateFlow()

    fun saveCustomer(){
        verifyFields()
    }

    fun onNameChanged(newName: String) {
        _name.value = newName
        _uiState.value = null
    }

    fun onEmailChanged(newEmail: String) {
        _email.value = newEmail
        _uiState.value = null
    }

    private fun verifyFields(){
        if (name.value.isEmpty() && email.value.isEmpty()){
            _uiState.value = UiState.EmptyFieldsError
        }
        else if (name.value.isEmpty()){
            _uiState.value = UiState.EmptyNameError
        }
        else if (email.value.isEmpty()){
            _uiState.value = UiState.EmptyEmailError
        }
        else if (!isEmailValid(email.value)){
            _uiState.value = UiState.InvalidEmailError
        }
        else {
            repository.addCustomer(name.value, email.value)
            _uiState.value = UiState.Success
        }
    }

    /**
     * Vérifie si l'email est valide.
     *
     * @param email L'email à vérifier.
     * @return `true` si l'email est valide, `false` sinon.
     */
    private fun isEmailValid(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$".toRegex()
        return emailRegex.matches(email)
    }
}


sealed class UiState(){
    object EmptyNameError: UiState()
    object EmptyEmailError: UiState()
    object EmptyFieldsError: UiState()
    object InvalidEmailError: UiState()
    object Success: UiState()
}
