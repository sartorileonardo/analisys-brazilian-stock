package br.com.company.stock.service

import br.com.company.stock.config.StockParametersConfig
import br.com.company.stock.mapper.StockMapper
import br.com.company.stock.repository.FundamentalStockRepository
import br.com.company.stock.repository.StockRepository
import io.mockk.MockKAnnotations
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import reactor.core.publisher.Mono


private const val VALID_TICKER = "ABEV3"

@ExtendWith(MockitoExtension::class)
internal class StockServiceTest {

    @Mock
    private lateinit var stockRepository: StockRepository;// = mockk(relaxed=true)

    @Mock
    private lateinit var fundamentalStockRepository: FundamentalStockRepository;// = mockk(relaxed=true)

    @Mock
    private lateinit var configuration: StockParametersConfig;// = mockk(relaxed=true)

    @Mock
    private lateinit var mapper: StockMapper;// = mockk(relaxed=true)

    @InjectMocks
    private lateinit var service: StockService;// = StockService(configuration, mapper, stockRepository, fundamentalStockRepository)

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `Test getAnalisys when already exists in repository`() {
        `when`(stockRepository.existsById(VALID_TICKER)).thenReturn(Mono.just(true))
        `when`(stockRepository.findById(VALID_TICKER)).thenReturn(Mono.just(mockk()))
        assertDoesNotThrow { service.getAnalisys(VALID_TICKER) }
    }

    @Test
    fun `Test getAnalisys when does not exist in repository`() {
        `when`(stockRepository.existsById(VALID_TICKER)).thenReturn(Mono.just(false))
        `when`(fundamentalStockRepository.existsById(VALID_TICKER)).thenReturn(Mono.just(true))
        `when`(stockRepository.findById(VALID_TICKER)).thenReturn(Mono.just(mockk()))
        `when`(fundamentalStockRepository.findById(VALID_TICKER)).thenReturn(Mono.just(mockk()))
        //`when`(service.getScoreEvaluation(anyString())).thenReturn(mockk())
        `when`(service.getExternalAnalisys(VALID_TICKER)).thenReturn(mockk())
        `when`(mapper.toEntity(mockk())).thenReturn(mockk())

        assertDoesNotThrow { service.getAnalisys(VALID_TICKER) }
    }

    @Test
    fun `Test getScoreEvaluation`() {
        //TODO: create test
        assertTrue(true)
    }

    @Test
    fun `Test getExternalAnalisys`() {
        //TODO: create test
        assertTrue(true)
    }

    //TODO: Add more test cases for other methods as needed
}
