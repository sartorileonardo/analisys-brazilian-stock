package br.com.company.stock.infrastructure.adapter.client.dto

data class CompanyDTO(
    val percentual_AcoesFreeFloat: String? = null,
    val margemLiquida: String? = null,
    val roe: String? = null,
    val lucros_Cagr5: String? = null,
    val setor_Atuacao_clean: String? = null,
    val injudicialProcess: String? = null,
    val liquidezCorrente: String? = null,
    val dividaliquida_PatrimonioLiquido: String? = null,
    val dividaLiquida_Ebit: String? = null,
    val segmento_Atuacao: String? = null,
    val bookName: String? = null
)