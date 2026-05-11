package com.kirabium.relayance.data

import com.kirabium.relayance.domain.model.Customer
import com.kirabium.relayance.domain.repository.CustomerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CustomerRepositoryImpl @Inject constructor(private val dataSource: DummyData) : CustomerRepository {
    override fun getCustomers(): Flow<List<Customer>> = dataSource.customers
}