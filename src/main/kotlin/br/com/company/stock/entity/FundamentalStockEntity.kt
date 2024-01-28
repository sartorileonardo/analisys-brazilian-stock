package br.com.company.stock.entity

import lombok.Getter
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Getter
@Document(collection = "fundamentals")
data class FundamentalStockEntity(
    @Id
    val ticker: String,
    val company: String? = null,
    val netMargin: Double?,
    val returnOnEquity: Double?,
    val returnOnAssets: Double?,
    val returnOnInvestedCapital: Double?,
    val cagrFiveYears: Double?,
    val sectorOfActivity: String? = null,
    val segmentOfActivity: String? = null,
    val companyIsInJudicialRecovery: Boolean?,
    val currentLiquidity: Double?,
    val netDebitOverNetEquity: Double?,
    val liabilitiesOverAssets: Double?,
    val marginLajir: Double?,
    val priceOnBookValue: Double?,
    val priceProfit: Double?,
    val priceLajir: Double?,
    val priceSalesRatio: Double?,
    val priceOnAssets: Double?,
    val evLajir: Double?,
    val operationSegment: String? = null,
    val createDate: LocalDate? = null
)
