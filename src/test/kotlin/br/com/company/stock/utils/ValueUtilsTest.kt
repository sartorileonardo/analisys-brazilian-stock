package br.com.company.stock.utils

import br.com.company.stock.utils.ValueUtils.Companion.getDoubleValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ValueUtilsTest {

    @Test
    fun `test valid double value`() {
        val validInput = "123.45"
        val result = getDoubleValue(validInput)
        assertEquals(123.45, result, "Valid input should return the expected double value")
    }

    @Test
    fun `test valid double value with negative sign`() {
        val validInput = "-789.01"
        val result = getDoubleValue(validInput)
        assertEquals(-789.01, result, "Valid input with a negative sign should return the expected double value")
    }

}
