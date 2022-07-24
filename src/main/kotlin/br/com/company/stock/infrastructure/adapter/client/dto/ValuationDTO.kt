package br.com.company.stock.infrastructure.adapter.client.dto

data class ValuationDTO(
    val valorMercado: String? = null,
    val patrimonioLiquido: String? = null,
    val pvp: String? = null,
    val ativoTotal: String? = null,
    val pAtivos: String? = null,
    val qntAcoes: String? = null,
    val vpa: String? = null,
    val valorFirma: String? = null,
    val ebitda: String? = null,
    val lucroLiquido: String? = null,
    val receitaLiquida: String? = null,
    val evEbitda: String? = null,
    val psr: String? = null,
    val roa: String? = null
)
