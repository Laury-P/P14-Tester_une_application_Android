package com.kirabium.relayance.domain.repository

import com.kirabium.relayance.domain.model.Customer
import kotlinx.coroutines.flow.Flow

interface CustomerRepository {
    fun getCustomers(): Flow<List<Customer>>
}


