package com.kirabium.relayance.viewModels

import com.kirabium.relayance.domain.model.Customer
import com.kirabium.relayance.domain.repository.CustomerRepository
import com.kirabium.relayance.ui.viewModel.MainViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.Calendar

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private val repository: CustomerRepository = mockk()
    private val testDispatcher = UnconfinedTestDispatcher()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init should fetch customers and update state flow`() = runTest {
        // --- GIVEN ---
        val fakeCustomers = listOf(
            Customer(1, "Alice", "alice@test.com", Calendar.getInstance().time),
            Customer(2, "Bob", "bob@test.com", Calendar.getInstance().time)
        )
        // On prépare le mock AVANT d'instancier le ViewModel
        coEvery { repository.getCustomers() } returns flowOf(fakeCustomers)

        // --- WHEN ---
        // L'appel à getCustomers() se déclenche ici, à la création
        val viewModel = MainViewModel(repository)

        // --- THEN ---
        assertEquals(fakeCustomers, viewModel.customers.value)
        assertEquals(2, viewModel.customers.value.size)

        coVerify(exactly = 1) { repository.getCustomers() }
    }

    @Test
    fun `init should set empty list when repository returns no customers`() = runTest {
        // --- GIVEN ---
        coEvery { repository.getCustomers() } returns flowOf(emptyList())

        // --- WHEN ---
        val viewModel = MainViewModel(repository)

        // --- THEN ---
        assert(viewModel.customers.value.isEmpty())
    }
}