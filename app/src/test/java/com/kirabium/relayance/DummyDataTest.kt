package com.kirabium.relayance

import com.kirabium.relayance.data.DummyData.generateDate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.util.Calendar

class DummyDataTest {

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
        assertEquals(expectedCalendar.get(Calendar.YEAR), resultCalendar.get(Calendar.YEAR), "L'année ne correspond pas")
        assertEquals(expectedCalendar.get(Calendar.MONTH), resultCalendar.get(Calendar.MONTH), "Le mois ne correspond pas")
        assertEquals(expectedCalendar.get(Calendar.DAY_OF_MONTH), resultCalendar.get(Calendar.DAY_OF_MONTH), "Le jour ne correspond pas")
    }

}