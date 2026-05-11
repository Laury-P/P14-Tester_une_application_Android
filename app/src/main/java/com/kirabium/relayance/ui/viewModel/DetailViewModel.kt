package com.kirabium.relayance.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kirabium.relayance.domain.model.Customer
import com.kirabium.relayance.domain.repository.CustomerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class DetailViewModel @Inject constructor(private val repository: CustomerRepository) : ViewModel()
{
    private val _customer : MutableStateFlow<Customer?> = MutableStateFlow(null)
    val customer: StateFlow<Customer?> = _customer.asStateFlow()

    fun loadCustomer(customerId: Int) {
        viewModelScope.launch {
            repository.getCustomerById(customerId).collect {
                _customer.value = it
            }
        }
    }
}