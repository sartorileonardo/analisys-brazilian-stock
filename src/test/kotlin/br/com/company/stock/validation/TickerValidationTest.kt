package br.com.company.stock.validation

import br.com.company.stock.exception.BusinessException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class TickerValidationTest {

    @Test
    fun `Test valid ticker`() {
        val ticker = "TAEE4"
        val result = TickerValidation.validateTicker(ticker)
        assertTrue(result)
    }

    @Test
    fun `Test ticker with only letters`() {
        val ticker = "ABCD"
        assertThrows(BusinessException::class.java) {
            TickerValidation.validateTicker(ticker)
        }
    }

    @Test
    fun `Test ticker with only numbers`() {
        val ticker = "12345"
        assertThrows(BusinessException::class.java) {
            TickerValidation.validateTicker(ticker)
        }
    }

    @Test
    fun `Test empty ticker`() {
        val ticker = ""
        assertThrows(BusinessException::class.java) {
            TickerValidation.validateTicker(ticker)
        }
    }

    @Test
    fun `Test ticker ending with 3`() {
        val ticker = "TAEE3"
        val result = TickerValidation.validateTicker(ticker)
        assertTrue(result)
    }

    @Test
    fun `Test ticker ending with 4`() {
        val ticker = "TAEE4"
        val result = TickerValidation.validateTicker(ticker)
        assertTrue(result)
    }

    @Test
    fun `Test ticker ending with 11`() {
        val ticker = "TAEE11"
        val result = TickerValidation.validateTicker(ticker)
        assertTrue(result)
    }

    @Test
    fun `Test BDR ticker`() {
        val ticker = "MSFT34"
        assertThrows(BusinessException::class.java) {
            TickerValidation.validateTicker(ticker)
        }
    }
}
