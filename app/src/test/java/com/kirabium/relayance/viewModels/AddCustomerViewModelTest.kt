package com.kirabium.relayance.viewModels

import com.kirabium.relayance.domain.repository.CustomerRepository
import com.kirabium.relayance.ui.viewModel.AddCustomerViewModel
import com.kirabium.relayance.ui.viewModel.UiState
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.just
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.AfterEach

@OptIn(ExperimentalCoroutinesApi::class)
class AddCustomerViewModelTest {

    private lateinit var viewModel: AddCustomerViewModel
    private lateinit var repository: CustomerRepository

    @BeforeEach
    fun setUp() {
        repository = mockk()
        viewModel = AddCustomerViewModel(repository)

    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `saveCustomer should call repository and set Success state when fields are valid`() {
        // Given
        viewModel.onNameChanged("Alice")
        viewModel.onEmailChanged("alice@example.com")
        every { repository.addCustomer("Alice", "alice@example.com") } just Runs

        // WHEN
        viewModel.saveCustomer()

        // THEN
        verify { repository.addCustomer("Alice", "alice@example.com") }
        assertEquals(UiState.Success, viewModel.uiState.value)
    }

    @Test
    fun `saveCustomer should set EmptyFieldsError when both fields are empty`() {
        // WHEN
        viewModel.saveCustomer()

        assertEquals(UiState.EmptyFieldsError, viewModel.uiState.value)
        // On s'assure que le repository n'a JAMAIS été appelé
        verify(exactly = 0) { repository.addCustomer(any(), any()) }
    }

    @Test
    fun `saveCustomer should set EmptyNameError when name is missing`() {
        // GIVEN
        viewModel.onEmailChanged("test@test.com")

        // WHEN
        viewModel.saveCustomer()

        // THEN
        assertEquals(UiState.EmptyNameError, viewModel.uiState.value)
    }

    @Test
    fun `saveCustomer should set EmptyEmailError when email is missing`() {
        // GIVEN
        viewModel.onNameChanged("Bob")

        // WHEN
        viewModel.saveCustomer()

        // THEN
        assertEquals(UiState.EmptyEmailError, viewModel.uiState.value)
    }

    @Test
    fun `onNameChanged should reset uiState to null`() {
        // GIVEN emptyFieldsError
        viewModel.saveCustomer()

        // WHEN
        viewModel.onNameChanged("Bob")

        // THEN
        assertEquals(null, viewModel.uiState.value)
    }

    @Test
    fun `onEmailChanged should reset uiState to null`() {
        // GIVEN emptyFieldsError
        viewModel.saveCustomer()

        // WHEN
        viewModel.onEmailChanged("bob@example.com")

        // THEN
        assertEquals(null, viewModel.uiState.value)
    }

    @Test
    fun `an incorect email should set InvalidEmailError`() {
        // GIVEN
        viewModel.onNameChanged("Alice")
        viewModel.onEmailChanged("aliceexample.com")

        // WHEN
        viewModel.saveCustomer()

        // THEN
        assertEquals(UiState.InvalidEmailError, viewModel.uiState.value)
    }


}