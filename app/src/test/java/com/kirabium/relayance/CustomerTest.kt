package com.kirabium.relayance

import com.kirabium.relayance.domain.model.Customer
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.Calendar


class CustomerTest {

    @Test
    fun isNewCustomer_whenCreatedLessThanThreeMonthsAgo_shouldReturnTrue() {
        // GIVEN
        val today = Calendar.getInstance()
        val customer = Customer(1, "Alice Wonderland", "alice@example.com", today.time)

        // WHEN
        val result = customer.isNewCustomer()

        // THEN
        assertTrue(result)
    }

    @Test
    fun isNewCustomer_whenCreatedMoreThanThreeMonthsAgo_shouldReturnFalse() {
        // GIVEN
        val sixMonthsAgo = Calendar.getInstance().apply {
            add(Calendar.MONTH, -6)
        }
        val customer = Customer(1, "Alice Wonderland", "alice@example.com", sixMonthsAgo.time)

        // WHEN
        val result = customer.isNewCustomer()

        // THEN
        assertFalse(result)
    }


}