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
class MainViewModel @Inject constructor(repository: CustomerRepository) : ViewModel()
{
    private val _customers : MutableStateFlow<List<Customer>> = MutableStateFlow(emptyList())
    val customers : StateFlow<List<Customer>> = _customers.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getCustomers().collect {
                _customers.value = it
            }
        }
    }

}



