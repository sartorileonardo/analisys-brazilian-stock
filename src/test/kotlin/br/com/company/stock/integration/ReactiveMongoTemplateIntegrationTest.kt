import br.com.company.stock.StockApplication
import br.com.company.stock.dto.Score
import br.com.company.stock.entity.FundamentalStockEntity
import br.com.company.stock.entity.StockEntity
import br.com.company.stock.repository.FundamentalStockRepository
import br.com.company.stock.repository.StockRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.test.context.ContextConfiguration
import reactor.test.StepVerifier
import java.time.LocalDate

@ContextConfiguration(classes = [StockApplication::class])
@DataMongoTest
class ReactiveMongoTemplateIntegrationTest {

    @Autowired
    private lateinit var mongoTemplate: ReactiveMongoTemplate

    @Autowired
    private lateinit var fundamentalStockRepository: FundamentalStockRepository

    @Autowired
    private lateinit var stockRepository: StockRepository

    @Test
    fun `test saving and retrieving fundamentalStockRepository from MongoDB`() {
        //Given
        val fundamentalStockEntity = FundamentalStockEntity(
            ticker = "VALE3",
            company = "Vale S.A.",
            netMargin = 0.15,
            returnOnEquity = 0.12,
            returnOnAssets = 0.08,
            returnOnInvestedCapital = 0.10,
            cagrFiveYears = 0.05,
            sectorOfActivity = "Mining",
            companyIsInJudicialRecovery = false,
            currentLiquidity = 1.5,
            netDebitOverNetEquity = 0.20,
            liabilitiesOverAssets = 0.40,
            marginLajir = 0.25,
            priceOnBookValue = 1.8,
            priceProfit = 12.5,
            priceLajir = 15.0,
            priceSalesRatio = 2.0,
            priceOnAssets = 1.2,
            evLajir = 12.0,
            operationSegment = "Iron Ore",
            createDate = LocalDate.of(2024, 4, 26)
        )

        //When
        val savedMono = mongoTemplate.save(fundamentalStockEntity)
        val savedData = savedMono.block() // Blocking call in tests is fine

        //Then
        assertEquals(fundamentalStockEntity, savedData)

        val retrievedMono = fundamentalStockRepository.findById("VALE3")
        StepVerifier.create(retrievedMono)
            .expectNext(fundamentalStockEntity)
            .verifyComplete()
    }

    @Test
    fun `test saving and retrieving stockRepository from MongoDB`() {
        //Given
        val stockEntity = StockEntity(
            ticker = "VALE3",
            estaEmSetorPerene = true,
            estaForaDeRecuperacaoJudicial = true,
            possuiBomNivelRetornoSobrePatrimonio = true,
            possuiBomNivelCrescimentoLucroNosUltimos5Anos = true,
            possuiBomNivelMargemLiquida = true,
            possuiBomNivelMargemEbit = true,
            possuiBomNivelDeCapacidadeDeQuitarDividaNoCurtoPrazo = true,
            possuiBomNivelDividaLiquidaSobrePatrimonioLiquido = true,
            possuiBomPrecoEmRelacaoAoValorPatrimonial = true,
            possuiNegociacaoAtiva = true,
            nomeEmpresa = "Vale S.A.",
            segmentoEmpresa = "Mineração",
            score = Score.GOOD
        )

        //When
        val savedMono = mongoTemplate.save(stockEntity)
        val savedData = savedMono.block() // Blocking call in tests is fine

        //Then
        assertEquals(stockEntity, savedData)

        val retrievedMono = stockRepository.findById("VALE3")
        StepVerifier.create(retrievedMono)
            .expectNext(stockEntity)
            .verifyComplete()
    }
}
