package com.kirabium.relayance.viewModels

import com.kirabium.relayance.domain.model.Customer
import com.kirabium.relayance.domain.repository.CustomerRepository
import com.kirabium.relayance.ui.viewModel.DetailViewModel
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
class DetailViewModelTest {

    private lateinit var viewModel: DetailViewModel
    // On simule le repository
    private val repository: CustomerRepository = mockk()

    // On définit un dispatcher de test
    private val testDispatcher = UnconfinedTestDispatcher()

    @BeforeEach
    fun setUp() {
        // On force Dispatchers.Main à utiliser notre dispatcher de test
        Dispatchers.setMain(testDispatcher)
        viewModel = DetailViewModel(repository)
    }

    @AfterEach
    fun tearDown() {
        // On réinitialise après chaque test
        Dispatchers.resetMain()
    }

    @Test
    fun `loadCustomer should update customer state flow when repository returns data`() = runTest {
        // --- GIVEN (Arangement) ---
        val fakeCustomer = Customer(
            id = 1,
            name = "Kirabium",
            email = "test@test.com",
            createdAt = Calendar.getInstance().time
        )
        val customerFlow = flowOf(fakeCustomer)

        coEvery { repository.getCustomerById(1) } returns customerFlow

        // --- WHEN (Action) ---
        viewModel.loadCustomer(1)

        // --- THEN (Vérification) ---
        // Avec UnconfinedTestDispatcher, le changement est immédiat
        assertEquals(fakeCustomer, viewModel.customer.value)

        coVerify(exactly = 1) { repository.getCustomerById(1) }
    }
}