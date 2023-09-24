package br.com.company.stock.client.dto

data class IndicatorsTickerDTO(
    val divida_patrimonio: String? = null,
    val preco_lucro: String? = null,
    val roe: String? = null,
    val percentage: String? = null,
    val variation_Up: String? = null,
    val pvp: String? = null,
    val ticker: String? = null,
    val sectorId: String? = null,
    val valor_mercado: String? = null,
    val negociacao_total_geral: String? = null
)
