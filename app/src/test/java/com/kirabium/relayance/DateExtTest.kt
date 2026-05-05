package com.kirabium.relayance

import com.kirabium.relayance.extension.DateExt.Companion.toHumanDate
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.Calendar
import java.util.Date
import java.util.Locale

class DateExtTest {

    private lateinit var originalLocale : Locale

    @BeforeEach
    fun setUp(){
        originalLocale = Locale.getDefault()

        Locale.setDefault(Locale.FRANCE)
    }

    @AfterEach
    fun tearDown(){
        Locale.setDefault(originalLocale)
    }

    companion object {
        @JvmStatic
        fun dateProvider() : List<Arguments> {
            return listOf(
                Arguments.of(createDate(2026, Calendar.JANUARY, 29), "29/01/2026"),
                Arguments.of(createDate(2026, Calendar.MAY, 1), "01/05/2026"),
                Arguments.of(createDate(2025, Calendar.DECEMBER, 20), "20/12/2025"),
                )
        }

        private fun createDate(year: Int, month: Int, day: Int) : Date {
            return Calendar.getInstance().apply {
                set(year, month, day)
            }.time
        }
    }

    @ParameterizedTest(name = "Date {0} should be formatted as {1}")
    @MethodSource("dateProvider")
    fun toHumanDate_shouldReturnFormattedDate(date: Date, expected: String) {
        // WHEN
        val result = date.toHumanDate()

        // THEN
        assertEquals(expected, result)

    }

}