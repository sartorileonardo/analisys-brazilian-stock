package br.com.company.stock.repository.entity

import br.com.company.stock.controller.dto.AvaliacaoGeral
import br.com.company.stock.controller.dto.StockAnalysisDTO
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "stocks")
data class StockAnalysisEntity(
    @Id
    val ticker: String,
    val estaEmSetorPerene: Boolean,
    val estaForaDeRecuperacaoJudicial: Boolean,
    val possuiBomNivelRetornoSobrePatrimonio: Boolean,
    val possuiBomNivelCrescimentoLucroNosUltimos5Anos: Boolean,
    val possuiBomNivelMargemLiquida: Boolean,
    val possuiBomNivelMargemEbit: Boolean,
    val possuiBomNivelDeCapacidadeDeQuitarDividaNoCurtoPrazo: Boolean,
    val possuiBomNivelDividaLiquidaSobrePatrimonioLiquido: Boolean,
    val possuiBomPrecoEmRelacaoAoValorPatrimonial: Boolean,
    val possuiNegociacaoAtiva: Boolean,
    val nomeEmpresa: String? = null,
    val segmentoEmpresa :String? = null,
    var avaliacaoGeral: AvaliacaoGeral? = null
) {

    companion object {
        fun toDTO(entity: StockAnalysisEntity): StockAnalysisDTO {
            return StockAnalysisDTO(
                ticker = entity.ticker,
                estaEmSetorPerene = entity.estaEmSetorPerene,
                estaForaDeRecuperacaoJudicial = entity.estaForaDeRecuperacaoJudicial,
                possuiBomNivelRetornoSobrePatrimonio = entity.possuiBomNivelRetornoSobrePatrimonio,
                possuiBomNivelCrescimentoLucroNosUltimos5Anos = entity.possuiBomNivelCrescimentoLucroNosUltimos5Anos,
                possuiBomNivelMargemLiquida = entity.possuiBomNivelMargemLiquida,
                possuiBomNivelMargemEbit = entity.possuiBomNivelMargemEbit,
                possuiBomNivelDeCapacidadeDeQuitarDividaNoCurtoPrazo = entity.possuiBomNivelDeCapacidadeDeQuitarDividaNoCurtoPrazo,
                possuiBomNivelDividaLiquidaSobrePatrimonioLiquido = entity.possuiBomNivelDividaLiquidaSobrePatrimonioLiquido,
                possuiBomPrecoEmRelacaoAoValorPatrimonial = entity.possuiBomPrecoEmRelacaoAoValorPatrimonial,
                possuiBomNivelPassivosSobreAtivos = entity.possuiNegociacaoAtiva,
                nomeEmpresa = entity.nomeEmpresa,
                segmentoEmpresa = entity.segmentoEmpresa,
                avaliacaoGeral = entity.avaliacaoGeral
            )
        }
    }

    override fun toString(): String{
        return "StockAnalysisDTO(\"ticker\":\"${ticker}\",\"estaEmSetorPerene\":${estaEmSetorPerene},\"estaForaDeRecuperacaoJudicial\":${estaForaDeRecuperacaoJudicial},\"possuiBomNivelRetornoSobrePatrimonio\":${possuiBomNivelRetornoSobrePatrimonio},\"possuiBomNivelCrescimentoLucroNosUltimos5Anos\":${possuiBomNivelCrescimentoLucroNosUltimos5Anos},\"possuiBomNivelMargemLiquida\":${possuiBomNivelMargemLiquida},\"possuiBomNivelDeCapacidadeDeQuitarDividaNoCurtoPrazo\":${possuiBomNivelDeCapacidadeDeQuitarDividaNoCurtoPrazo},\"possuiBomNivelDividaLiquidaSobrePatrimonioLiquido\":${possuiBomNivelDividaLiquidaSobrePatrimonioLiquido},\"possuiBomPrecoEmRelacaoAoValorPatrimonial\":${possuiBomPrecoEmRelacaoAoValorPatrimonial},\"nomeEmpresa\":\"${nomeEmpresa}\",\"segmentoEmpresa\":\"${segmentoEmpresa}\",\"avaliacaoGeral\":\"${avaliacaoGeral.toString()}\")"
    }
}
