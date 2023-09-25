package br.com.company.stock.mapper

import br.com.company.stock.dto.StockAnalisysDTO
import br.com.company.stock.entity.StockEntity
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class StockMapperTest {

    private lateinit var stockMapper: StockMapper

    @BeforeEach
    fun setUp() {
        //TODO: init variables
    }

    @Test
    fun `Test toEntity`() {
        //TODO: create test
        assertTrue(true)
    }

    @Test
    fun `Test toDTO`() {
        //TODO: create test
        assertTrue(true)
    }
}
