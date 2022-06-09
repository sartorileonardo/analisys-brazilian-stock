package br.com.company.stock.controller.dto

import br.com.company.stock.repository.entity.StockAnalysisEntity

class StockAnalysisDTO(
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
        fun toEntity(dto: StockAnalysisDTO): StockAnalysisEntity {
            return StockAnalysisEntity(
                ticker = dto.ticker,
                estaEmSetorPerene = dto.estaEmSetorPerene,
                estaForaDeRecuperacaoJudicial = dto.estaForaDeRecuperacaoJudicial,
                possuiBomNivelDeAcoesDisponiveisNoMercado = dto.possuiBomNivelDeAcoesDisponiveisNoMercado,
                possuiBomNivelRetornoSobrePatrimonio = dto.possuiBomNivelRetornoSobrePatrimonio,
                possuiBomNivelCrescimentoLucroNosUltimos5Anos = dto.possuiBomNivelCrescimentoLucroNosUltimos5Anos,
                possuiBomNivelMargemLiquida = dto.possuiBomNivelMargemLiquida,
                possuiBomNivelDeCapacidadeDeQuitarDividaNoCurtoPrazo = dto.possuiBomNivelDeCapacidadeDeQuitarDividaNoCurtoPrazo,
                possuiBomNivelDividaLiquidaSobrePatrimonioLiquido = dto.possuiBomNivelDividaLiquidaSobrePatrimonioLiquido,
                possuiBomNivelDividaLiquidaSobreResultadoOperacional = dto.possuiBomNivelDividaLiquidaSobreResultadoOperacional,
                possuiBomPrecoEmRelacaoAoLucroAssimComoValorPatrimonial = dto.possuiBomPrecoEmRelacaoAoLucroAssimComoValorPatrimonial,
                possuiDireitoDeVendaDeAcoesIgualAoAcionistaControlador = dto.possuiDireitoDeVendaDeAcoesIgualAoAcionistaControlador
            )
        }
    }
}