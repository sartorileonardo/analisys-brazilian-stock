package br.com.company.stock.infrastructure.adapter.client.dto

data class PaperDTO(
    val tagAlong: String? = null,
    val indicadores: List<IndicadorDTO>? = null
)
