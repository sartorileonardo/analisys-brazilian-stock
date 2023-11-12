package br.com.company.stock.utils

import br.com.company.stock.utils.ValueUtils.Companion.getDoubleValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class ValueUtilsTest {

    @Test
    fun `test valid double value`() {
        val validInput = "123.45"
        val result = getDoubleValue(validInput)
        assertEquals(123.45, result, "Valid input should return the expected double value")
    }

    @Test
    fun `test valid double value with commas and percentage`() {
        val invalidInput = "1,234.56%"
        val exception = assertThrows(IllegalArgumentException::class.java) {
            getDoubleValue(invalidInput)
        }
        assertEquals(
            "Invalid double value: $invalidInput",
            exception.message,
            "Invalid input should throw an exception"
        )
    }

    @Test
    fun `test valid double value with negative sign`() {
        val validInput = "-789.01"
        val result = getDoubleValue(validInput)
        assertEquals(-789.01, result, "Valid input with a negative sign should return the expected double value")
    }

    @Test
    fun `test invalid double value`() {
        val invalidInput = "abc"
        val exception = assertThrows(IllegalArgumentException::class.java) {
            getDoubleValue(invalidInput)
        }
        assertEquals(
            "Invalid double value: $invalidInput",
            exception.message,
            "Invalid input should throw an exception"
        )
    }

    @Test
    fun `test empty input`() {
        val emptyInput = ""
        val exception = assertThrows(IllegalArgumentException::class.java) {
            getDoubleValue(emptyInput)
        }
        assertEquals("Invalid double value: $emptyInput", exception.message, "Empty input should throw an exception")
    }
}
