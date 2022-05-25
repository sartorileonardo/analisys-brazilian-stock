package br.com.company.stock.client.dto

import com.google.gson.GsonBuilder
import java.util.*

data class ResponseDTO(
    val valuation: ValuationDTO? = null,
    val indicatorsTicker: IndicatorsTickerDTO? = null,
    val paper: PaperDTO? = null,
    val pvp: String? = null,
    val precoSobreValorPatrimonial: String? = null,
    val precoSobreLucro: String? = null,
    val company: CompanyDTO? = null,
) {
    companion object {
        fun parseMapToDto(map: Map<String, Objects>): ResponseDTO {
            return map.mapTo()
        }

        inline fun <reified ResponseDTO : Any> Any.mapTo(): ResponseDTO = GsonBuilder().create().run {
            toJson(this@mapTo).let { fromJson(it, ResponseDTO::class.java) }
        }
    }
}
