package br.com.company.stock.service

import br.com.company.stock.entity.FundamentalStockEntity
import br.com.company.stock.repository.FundamentalStockRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import reactor.core.publisher.Mono


@ExtendWith(MockitoExtension::class)
internal class FundamentalServiceTest {

    @Mock
    lateinit var fundamentalStockRepository: FundamentalStockRepository

    @InjectMocks
    lateinit var fundamentalService: FundamentalService

    /*
    TODO: Pending adjust
    @Test
    fun `saveFundamentalStock - stock entity already exists`() {

        val fundamentalMock = FundamentalStockEntity(
            ticker = "LWSA3",
            company = "Locaweb",
            netMargin = 0.25,
            returnOnEquity = 0.48,
            returnOnAssets = 0.21,
            returnOnInvestedCapital = 0.35,
            cagrFiveYears = 0.18,
            sectorOfActivity = "Technology",
            companyIsInJudicialRecovery = false,
            currentLiquidity = 1.42,
            netDebitOverNetEquity = 0.56,
            liabilitiesOverAssets = 0.67,
            marginLajir = 0.32,
            priceOnBookValue = 5.23,
            priceProfit = 27.46,
            priceLajir = 18.95,
            priceSalesRatio = 7.12,
            priceOnAssets = 1.98,
            evLajir = 25.63,
            operationSegment = "Serviços de Internet"
        )

        `when`(fundamentalService.getTodayDate()).thenReturn(LocalDate.of(2023, 12, 15))
        `when`(fundamentalService.isFundamentalExpired(fundamentalService.getTodayDate(),
            fundamentalMock.createDate!!
        )).thenReturn(false)
        `when`(fundamentalStockRepository.existsById(fundamentalMock.ticker)).thenReturn(Mono.just(false))
        `when`(fundamentalStockRepository.save(any())).thenReturn(Mono.just(fundamentalMock))

        val existingEntity = fundamentalService.saveFundamentalStock(
            fundamentalMock.ticker,
            fundamentalMock.company!!,
            1.00,
            5.00,
            5.00,
            5.00,
            5.00,
            fundamentalMock.sectorOfActivity!!,
            false,
            2.00,
            2.00,
            2.00,
            2.00,
            2.00,
            2.00,
            2.00,
            2.00,
            2.00,
            2.00,
        )

        `when`(fundamentalStockRepository.existsById(fundamentalMock.ticker)).thenReturn(Mono.just(true))
        `when`(fundamentalStockRepository.findById(fundamentalMock.ticker)).thenReturn(existingEntity)

        val result = fundamentalService.saveFundamentalStock(
            fundamentalMock.ticker,
            fundamentalMock.company!!,
            1.00,
            5.00,
            5.00,
            5.00,
            5.00,
            fundamentalMock.sectorOfActivity!!,
            false,
            2.00,
            2.00,
            2.00,
            2.00,
            2.00,
            2.00,
            2.00,
            2.00,
            2.00,
            2.00,
        )

        assertEquals(result, existingEntity)
        verify(fundamentalStockRepository, times(2)).existsById(fundamentalMock.ticker)
        verify(fundamentalStockRepository).findById(fundamentalMock.ticker)
        verify(fundamentalStockRepository).save(any())
    }

    @Test
    fun `saveFundamentalStock - stock entity does not exist`() {
        val fundamentalMock = FundamentalStockEntity(
            ticker = "LWSA3",
            company = "Locaweb",
            netMargin = 0.25,
            returnOnEquity = 0.48,
            returnOnAssets = 0.21,
            returnOnInvestedCapital = 0.35,
            cagrFiveYears = 0.18,
            sectorOfActivity = "Technology",
            companyIsInJudicialRecovery = false,
            currentLiquidity = 1.42,
            netDebitOverNetEquity = 0.56,
            liabilitiesOverAssets = 0.67,
            marginLajir = 0.32,
            priceOnBookValue = 5.23,
            priceProfit = 27.46,
            priceLajir = 18.95,
            priceSalesRatio = 7.12,
            priceOnAssets = 1.98,
            evLajir = 25.63,
            operationSegment = "Serviços de Internet"
        )

        `when`(fundamentalStockRepository.existsById(fundamentalMock.ticker)).thenReturn(Mono.just(false))
        `when`(fundamentalStockRepository.save(any())).thenReturn(Mono.just(fundamentalMock))

        val result = fundamentalService.saveFundamentalStock(
            fundamentalMock.ticker,
            fundamentalMock.company!!,
            1.00,
            5.00,
            5.00,
            5.00,
            5.00,
            fundamentalMock.sectorOfActivity!!,
            false,
            2.00,
            2.00,
            2.00,
            2.00,
            2.00,
            2.00,
            2.00,
            2.00,
            2.00,
            2.00
        )

        Assertions.assertThat(result.block()).isSameAs(fundamentalMock)

        verify(fundamentalStockRepository).existsById(fundamentalMock.ticker)
        verify(fundamentalStockRepository).save(any())
    }
*/
    @Test
    fun `fundamentalStockEntityExist - stock entity exists`() {
        val ticker = "TAEE3"

        `when`(fundamentalStockRepository.existsById(ticker)).thenReturn(Mono.just(true))

        assertTrue(fundamentalService.fundamentalStockEntityExist(ticker))
        verify(fundamentalStockRepository).existsById(ticker)
    }

    @Test
    fun `fundamentalStockEntityExist - stock entity does not exist`() {
        val ticker = "TAEE3"

        `when`(fundamentalStockRepository.existsById(ticker)).thenReturn(Mono.just(false))

        assertFalse(fundamentalService.fundamentalStockEntityExist(ticker))
        verify(fundamentalStockRepository).existsById(ticker)
    }

    @Test
    fun `findById - stock entity found`() {
        val foundEntity = FundamentalStockEntity(
            ticker = "LWSA3",
            company = "Locaweb",
            netMargin = 0.25,
            returnOnEquity = 0.48,
            returnOnAssets = 0.21,
            returnOnInvestedCapital = 0.35,
            cagrFiveYears = 0.18,
            sectorOfActivity = "Technology",
            companyIsInJudicialRecovery = false,
            currentLiquidity = 1.42,
            netDebitOverNetEquity = 0.56,
            liabilitiesOverAssets = 0.67,
            marginLajir = 0.32,
            priceOnBookValue = 5.23,
            priceProfit = 27.46,
            priceLajir = 18.95,
            priceSalesRatio = 7.12,
            priceOnAssets = 1.98,
            evLajir = 25.63,
            operationSegment = "Serviços de Internet"
        )

        `when`(fundamentalStockRepository.findById(foundEntity.ticker)).thenReturn(Mono.just(foundEntity))

        val result = fundamentalService.findById(foundEntity.ticker)

        assertEquals(result.block(), foundEntity)

        verify(fundamentalStockRepository).findById(foundEntity.ticker)
    }

    @Test
    fun `findById - stock entity not found`() {
        val ticker = "TAEE3"

        `when`(fundamentalStockRepository.findById(ticker)).thenReturn(Mono.empty())

        val result = fundamentalService.findById(ticker)

        assertNull(result.block())
        verify(fundamentalStockRepository).findById(ticker)
    }


}
