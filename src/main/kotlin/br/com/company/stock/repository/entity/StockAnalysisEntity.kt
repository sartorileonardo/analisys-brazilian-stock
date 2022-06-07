package br.com.company.stock.repository.entity

import br.com.company.stock.controller.dto.StockAnalysisDTO
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "stocks")
class StockAnalysisEntity(
    @Id
    val ticker: String,
    val estaEmSetorPerene: Boolean,
    val estaForaDeRecuperacaoJudicial: Boolean,
    val possuiBomNivelDeAcoesDisponiveisNoMercado: Boolean,
    val possuiBomNivelRetornoSobrePatrimonio: Boolean,
    val possuiBomNivelCrescimentoLucroNosUltimos5Anos: Boolean,
    val possuiBomNivelMargemLiquida: Boolean,
    val possuiBomNivelDeCapacidadeDeQuitarDividaNoCurtoPrazo: Boolean,
    val possuiBomNivelDividaLiquidaSobrePatrimonioLiquido: Boolean,
    val possuiBomNivelDividaLiquidaSobreResultadoOperacional: Boolean,
    val possuiBomPrecoEmRelacaoAoLucroAssimComoValorPatrimonial: Boolean,
    val possuiDireitoDeVendaDeAcoesIgualAoAcionistaControlador: Boolean
) {

    companion object {
        fun toDTO(entity: StockAnalysisEntity): StockAnalysisDTO {
            return StockAnalysisDTO(
                ticker = entity.ticker,
                estaEmSetorPerene = entity.estaEmSetorPerene,
                estaForaDeRecuperacaoJudicial = entity.estaForaDeRecuperacaoJudicial,
                possuiBomNivelDeAcoesDisponiveisNoMercado = entity.possuiBomNivelDeAcoesDisponiveisNoMercado,
                possuiBomNivelRetornoSobrePatrimonio = entity.possuiBomNivelRetornoSobrePatrimonio,
                possuiBomNivelCrescimentoLucroNosUltimos5Anos = entity.possuiBomNivelCrescimentoLucroNosUltimos5Anos,
                possuiBomNivelMargemLiquida = entity.possuiBomNivelMargemLiquida,
                possuiBomNivelDeCapacidadeDeQuitarDividaNoCurtoPrazo = entity.possuiBomNivelDeCapacidadeDeQuitarDividaNoCurtoPrazo,
                possuiBomNivelDividaLiquidaSobrePatrimonioLiquido = entity.possuiBomNivelDividaLiquidaSobrePatrimonioLiquido,
                possuiBomNivelDividaLiquidaSobreResultadoOperacional = entity.possuiBomNivelDividaLiquidaSobreResultadoOperacional,
                possuiBomPrecoEmRelacaoAoLucroAssimComoValorPatrimonial = entity.possuiBomPrecoEmRelacaoAoLucroAssimComoValorPatrimonial,
                possuiDireitoDeVendaDeAcoesIgualAoAcionistaControlador = entity.possuiDireitoDeVendaDeAcoesIgualAoAcionistaControlador
            )
        }
    }
}
