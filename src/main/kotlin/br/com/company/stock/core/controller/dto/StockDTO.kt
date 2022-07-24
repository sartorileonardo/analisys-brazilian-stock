package br.com.company.stock.core.controller.dto

data class StockDTO(
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
    val possuiBomNivelPassivosSobreAtivos: Boolean,
    val nomeEmpresa: String? = null,
    val segmentoEmpresa: String? = null,
    val avaliacaoGeral: AvaliacaoGeral? = null
) {
    override fun toString(): String{
        return "StockDTO(\"ticker\":\"${ticker}\",\"estaEmSetorPerene\":${estaEmSetorPerene},\"estaForaDeRecuperacaoJudicial\":${estaForaDeRecuperacaoJudicial},\"possuiBomNivelRetornoSobrePatrimonio\":${possuiBomNivelRetornoSobrePatrimonio},\"possuiBomNivelCrescimentoLucroNosUltimos5Anos\":${possuiBomNivelCrescimentoLucroNosUltimos5Anos},\"possuiBomNivelMargemLiquida\":${possuiBomNivelMargemLiquida},\"possuiBomNivelDeCapacidadeDeQuitarDividaNoCurtoPrazo\":${possuiBomNivelDeCapacidadeDeQuitarDividaNoCurtoPrazo},\"possuiBomNivelDividaLiquidaSobrePatrimonioLiquido\":${possuiBomNivelDividaLiquidaSobrePatrimonioLiquido},\"possuiBomPrecoEmRelacaoAoValorPatrimonial\":${possuiBomPrecoEmRelacaoAoValorPatrimonial},\"nomeEmpresa\":\"${nomeEmpresa}\",\"segmentoEmpresa\":\"${segmentoEmpresa}\",\"avaliacaoGeral\":\"${avaliacaoGeral.toString()}\")"
    }
}