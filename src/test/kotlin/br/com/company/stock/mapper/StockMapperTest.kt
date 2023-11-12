package br.com.company.stock.mapper

import br.com.company.stock.dto.Score
import br.com.company.stock.dto.StockAnalisysDTO
import br.com.company.stock.entity.StockEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.MockitoAnnotations.openMocks

class StockMapperTest {

    @InjectMocks
    private lateinit var stockMapper: StockMapper

    @BeforeEach
    fun setUp() {
        openMocks(this)
    }


    @Test
    fun `should map DTO to entity`() {
        val dto = StockAnalisysDTO(
            ticker = "PETR4",
            estaEmSetorPerene = true,
            estaForaDeRecuperacaoJudicial = true,
            possuiBomNivelRetornoSobrePatrimonio = true,
            possuiBomNivelCrescimentoLucroNosUltimos5Anos = true,
            possuiBomNivelMargemLiquida = true,
            possuiBomNivelMargemEbit = true,
            possuiBomNivelDeCapacidadeDeQuitarDividaNoCurtoPrazo = true,
            possuiBomNivelDividaLiquidaSobrePatrimonioLiquido = true,
            possuiBomPrecoEmRelacaoAoValorPatrimonial = true,
            possuiBomNivelPassivosSobreAtivos = true,
            nomeEmpresa = "Petrobras",
            segmentoEmpresa = "Petróleo e gás",
            score = Score.GOOD
        )

        val entity = stockMapper.toEntity(dto)

        assertEquals(dto.ticker, entity.ticker)
        assertEquals(dto.estaEmSetorPerene, entity.estaEmSetorPerene)
        assertEquals(dto.estaForaDeRecuperacaoJudicial, entity.estaForaDeRecuperacaoJudicial)
        assertEquals(dto.possuiBomNivelRetornoSobrePatrimonio, entity.possuiBomNivelRetornoSobrePatrimonio)
        assertEquals(
            dto.possuiBomNivelCrescimentoLucroNosUltimos5Anos,
            entity.possuiBomNivelCrescimentoLucroNosUltimos5Anos
        )
        assertEquals(dto.possuiBomNivelMargemLiquida, entity.possuiBomNivelMargemLiquida)
        assertEquals(dto.possuiBomNivelMargemEbit, entity.possuiBomNivelMargemEbit)
        assertEquals(
            dto.possuiBomNivelDeCapacidadeDeQuitarDividaNoCurtoPrazo,
            entity.possuiBomNivelDeCapacidadeDeQuitarDividaNoCurtoPrazo
        )
        assertEquals(
            dto.possuiBomNivelDividaLiquidaSobrePatrimonioLiquido,
            entity.possuiBomNivelDividaLiquidaSobrePatrimonioLiquido
        )
        assertEquals(dto.possuiBomPrecoEmRelacaoAoValorPatrimonial, entity.possuiBomPrecoEmRelacaoAoValorPatrimonial)
        assertEquals(dto.nomeEmpresa, entity.nomeEmpresa)
        assertEquals(dto.segmentoEmpresa, entity.segmentoEmpresa)
        assertEquals(dto.score, entity.score)
    }

    @Test
    fun `should map entity to DTO`() {
        val entity = StockEntity(
            ticker = "PETR4",
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
            nomeEmpresa = "Petrobras",
            segmentoEmpresa = "Petróleo e gás",
            score = Score.GOOD
        )

        val dto = stockMapper.toDTO(entity)

        assertEquals(entity.ticker, dto.ticker)
        assertEquals(entity.estaEmSetorPerene, dto.estaEmSetorPerene)
    }
}
