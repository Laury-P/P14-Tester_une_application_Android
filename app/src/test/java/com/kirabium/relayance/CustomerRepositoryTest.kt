package com.kirabium.relayance

import com.kirabium.relayance.data.CustomerRepositoryImpl
import com.kirabium.relayance.data.DummyData
import com.kirabium.relayance.domain.model.Customer
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.Calendar

@OptIn(ExperimentalCoroutinesApi::class)
class CustomerRepositoryImplTest {

    private lateinit var repository: CustomerRepositoryImpl
    private val dataSource: DummyData = mockk(relaxed = true)

    @BeforeEach
    fun setUp() {
        repository = CustomerRepositoryImpl(dataSource)
    }

    @Test
    fun `getCustomers should return list from dataSource`() = runTest {
        // --- GIVEN ---
        val expectedCustomers = listOf(
            Customer(1, "Alice", "alice@test.com", Calendar.getInstance().time),
            Customer(2, "Bob", "bob@test.com", Calendar.getInstance().time)
        )
        // On simule le StateFlow ou Flow de la dataSource
        every { dataSource.customers } returns MutableStateFlow(expectedCustomers)

        // --- WHEN ---
        val result = repository.getCustomers().first()

        // --- THEN ---
        assertEquals(expectedCustomers, result)
        assertEquals(2, result.size)
    }

    @Test
    fun `getCustomerById should return correct customer when id exists`() = runTest {
        // --- GIVEN ---
        val alice = Customer(1, "Alice", "alice@test.com", Calendar.getInstance().time)
        val bob = Customer(2, "Bob", "bob@test.com", Calendar.getInstance().time)
        every { dataSource.customers } returns MutableStateFlow(listOf(alice, bob))

        // --- WHEN ---
        val result = repository.getCustomerById(2).first()

        // --- THEN ---
        assertEquals(bob, result)
        assertEquals("Bob", result?.name)
    }

    @Test
    fun `getCustomerById should return null when id does not exist`() = runTest {
        // --- GIVEN ---
        every { dataSource.customers } returns MutableStateFlow(listOf(Customer(1, "Alice", "", Calendar.getInstance().time)))

        // --- WHEN ---
        val result = repository.getCustomerById(99).first()

        // --- THEN ---
        assertNull(result)
    }

    @Test
    fun `addCustomer should call dataSource addCustomer`() {
        // --- WHEN ---
        repository.addCustomer("Charlie", "charlie@test.com")

        // --- THEN ---
        verify(exactly = 1) { dataSource.addCustomer("Charlie", "charlie@test.com") }
    }
}