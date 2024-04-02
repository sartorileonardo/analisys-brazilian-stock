package br.com.company.stock.client.dto

import com.google.gson.GsonBuilder

data class ResponseDTO(
    val valuation: ValuationDTO? = null,
    val indicatorsTicker: IndicatorsTickerDTO? = null,
    val otherIndicators: OtherIndicatorsDTO? = null,
    val paper: PaperDTO? = null,
    val pvp: String? = null,
    val precoSobreValorPatrimonial: String? = null,
    val precoSobreLucro: String? = null,
    val company: CompanyDTO? = null,
    val isNegotiationActive: Boolean
) {
    companion object {
        fun parseMapToDto(map: Map<*, *>?): ResponseDTO {
            return map!!.mapTo()
        }

        inline fun <reified ResponseDTO : Any> Any.mapTo(): ResponseDTO = GsonBuilder().create().run {
            toJson(this@mapTo).let { fromJson(it, ResponseDTO::class.java) }
        }
    }
}
