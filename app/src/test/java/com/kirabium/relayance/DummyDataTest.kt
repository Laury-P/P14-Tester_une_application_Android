package com.kirabium.relayance

import com.kirabium.relayance.data.DummyData
import com.kirabium.relayance.data.DummyData.generateDate
import com.kirabium.relayance.domain.model.Customer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.util.Calendar

class DummyDataTest {
    // On stocke la liste initiale pour pouvoir la remettre à zéro après chaque test
    private val initialList = listOf(
        Customer(1, "Alice Wonderland", "alice@example.com", DummyData.generateDate(12)),
        Customer(2, "Bob Builder", "bob@example.com", DummyData.generateDate(6)),
        Customer(3, "Charlie Chocolate", "charlie@example.com", DummyData.generateDate(3)),
        Customer(4, "Diana Dream", "diana@example.com", DummyData.generateDate(1)),
        Customer(5, "Evan Escape", "evan@example.com", DummyData.generateDate(0)),
    )
    @BeforeEach
    fun setUp() {
        // RÉINITIALISATION : On remet la liste par défaut avant chaque test
        DummyData.customers.value = initialList
    }

    @ParameterizedTest(name = "Mois en arrière : {0}")
    @ValueSource(ints = [0, 1, 6, 12, 24]) // On teste aujourd'hui, 1 mois, 6 mois, 1 an et 2 ans
    fun generateDate_shouldReturnDateWithCorrectOffset(monthsBack: Int) {
        // GIVEN
        val expectedCalendar = Calendar.getInstance()
        expectedCalendar.add(Calendar.MONTH, -monthsBack)

        // WHEN
        val resultDate = generateDate(monthsBack)

        // THEN
        val resultCalendar = Calendar.getInstance().apply { time = resultDate }

        // On compare les éléments importants (Année, Mois, Jour) pour éviter les problèmes de millisecondes
        assertEquals(
            expectedCalendar.get(Calendar.YEAR),
            resultCalendar.get(Calendar.YEAR),
            "L'année ne correspond pas"
        )
        assertEquals(
            expectedCalendar.get(Calendar.MONTH),
            resultCalendar.get(Calendar.MONTH),
            "Le mois ne correspond pas"
        )
        assertEquals(
            expectedCalendar.get(Calendar.DAY_OF_MONTH),
            resultCalendar.get(Calendar.DAY_OF_MONTH),
            "Le jour ne correspond pas"
        )
    }




    @Test
    fun `addCustomer should append a new customer with incremented ID`() {
        // --- GIVEN ---
        val name = "Test User"
        val email = "test@example.com"
        val expectedId = 6 // Puisqu'il y en a 5 au départ

        // --- WHEN ---
        DummyData.addCustomer(name, email)

        // --- THEN ---
        val currentCustomers = DummyData.customers.value
        assertEquals(6, currentCustomers.size)

        val lastCustomer = currentCustomers.last()
        assertEquals(expectedId, lastCustomer.id)
        assertEquals(name, lastCustomer.name)
        assertEquals(email, lastCustomer.email)
    }

    @Test
    fun `addCustomer multiple times should keep incrementing IDs`() {
        // --- WHEN ---
        DummyData.addCustomer("User 6", "u6@test.com") // ID 6
        DummyData.addCustomer("User 7", "u7@test.com") // ID 7

        // --- THEN ---
        val lastId = DummyData.customers.value.last().id
        assertEquals(7, lastId)
    }
}

