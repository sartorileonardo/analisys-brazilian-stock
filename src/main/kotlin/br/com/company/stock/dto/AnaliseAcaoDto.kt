package br.com.company.stock.dto

data class AnaliseAcaoDto(
    val estaEmSetorPerene: Boolean,
    val estaForaDeRecuperacaoJudicial: Boolean,
    val possuiBomNivelFreeFloat: Boolean,
    val possuiBomNivelROE: Boolean,
    val possuiBomNivelCAGRLucro: Boolean,
    val possuiBomNivelMargemLiquida: Boolean,
    val possuiBomNivelLiquidez: Boolean,
    val possuiBomNivelDividaLiquidaSobrePatrimonioLiquido: Boolean,
    val possuiBomNivelDividaLiquidaSobreEbitda: Boolean,
    val possuiBomPrecoEmRelacaoAoLucroAssimComoValorPatrimonial: Boolean
)