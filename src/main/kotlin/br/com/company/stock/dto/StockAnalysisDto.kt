package br.com.company.stock.dto

data class StockAnalysisDto(
    val estaEmSetorPerene: Boolean,
    val estaForaDeRecuperacaoJudicial: Boolean,
    val possuiBomNivelFreeFloat: Boolean,
    val possuiBomNivelRetornoSobrePatrimonio: Boolean,
    val possuiBomNivelCrescimentoLucroNosUltimos5Anos: Boolean,
    val possuiBomNivelMargemLiquida: Boolean,
    val possuiBomNivelLiquidez: Boolean,
    val possuiBomNivelDividaLiquidaSobrePatrimonioLiquido: Boolean,
    val possuiBomNivelDividaLiquidaSobreEbitda: Boolean,
    val possuiBomPrecoEmRelacaoAoLucroAssimComoValorPatrimonial: Boolean
)