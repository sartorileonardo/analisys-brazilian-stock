package br.com.company.stock.service

import br.com.company.stock.entity.FundamentalStockEntity
import br.com.company.stock.repository.FundamentalStockRepository
import br.com.company.stock.utils.DateUtils.Companion.getTodayDate
import br.com.company.stock.utils.DateUtils.Companion.isFundamentalExpired
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class FundamentalService(
    val fundamentalStockRepository: FundamentalStockRepository
) {
    companion object {
        var LOGGER: Logger? = LoggerFactory.getLogger(FundamentalService::class.java)
    }

    @Cacheable(value = ["fundamentals"])
    fun saveFundamentalStock(
        ticker: String,
        company: String,
        netMargin: Double?,
        returnOnEquity: Double?,
        returnOnAssets: Double?,
        returnOnInvestedCapital: Double?,
        cagrFiveYears: Double?,
        sectorOfActivity: String? = null,
        segmentOfActivity: String? = null,
        companyIsInJudicialRecovery: Boolean?,
        currentLiquidity: Double?,
        netDebitOverNetEquity: Double?,
        liabilitiesOverAssets: Double?,
        marginLajir: Double?,
        priceOnBookValue: Double?,
        priceProfit: Double?,
        priceLajir: Double?,
        priceSalesRatio: Double?,
        priceOnAssets: Double?,
        evLajir: Double?
    ): Mono<FundamentalStockEntity> {
        if (fundamentalStockEntityExist(ticker) && !isFundamentalExpired(
                getTodayDate(),
                findById(ticker).block()!!.createDate!!
            )
        ) {
            return findById(ticker)
        }
        val today = getTodayDate()
        val fundamentalStockEntity = FundamentalStockEntity(
            ticker = ticker,
            company = company,
            netMargin = netMargin,
            returnOnEquity = returnOnEquity,
            returnOnAssets = returnOnAssets,
            returnOnInvestedCapital = returnOnInvestedCapital,
            cagrFiveYears = cagrFiveYears,
            sectorOfActivity = sectorOfActivity,
            segmentOfActivity = segmentOfActivity,
            companyIsInJudicialRecovery = companyIsInJudicialRecovery,
            currentLiquidity = currentLiquidity,
            netDebitOverNetEquity = netDebitOverNetEquity,
            liabilitiesOverAssets = liabilitiesOverAssets,
            marginLajir = marginLajir,
            priceOnBookValue = priceOnBookValue,
            priceProfit = priceProfit,
            priceLajir = priceLajir,
            priceSalesRatio = priceSalesRatio,
            priceOnAssets = priceOnAssets,
            evLajir = evLajir,
            createDate = today
        )
        return fundamentalStockRepository.save(fundamentalStockEntity)
    }

    fun fundamentalStockEntityExist(ticker: String) =
        fundamentalStockRepository.existsById(ticker).block()!!

    fun findById(ticker: String): Mono<FundamentalStockEntity> {
        return fundamentalStockRepository.findById(ticker)
    }

}
