package br.com.company.stock.controller.dto

import br.com.company.stock.repository.entity.StockAnalysisEntity

data class StockAnalysisDTO(
    val ticker: String,
    val estaEmSetorPerene: Boolean,
    val estaForaDeRecuperacaoJudicial: Boolean,
    val possuiBomNivelRetornoSobrePatrimonio: Boolean,
    val possuiBomNivelCrescimentoLucroNosUltimos5Anos: Boolean,
    val possuiBomNivelMargemLiquida: Boolean,
    val possuiBomNivelMargemEbit: Boolean,
    val possuiBomNivelDeCapacidadeDeQuitarDividaNoCurtoPrazo: Boolean,
    val possuiBomNivelDividaLiquidaSobrePatrimonioLiquido: Boolean,
    val possuiBomPrecoEmRelacaoAoLucroAssimComoValorPatrimonial: Boolean,
    val possuiBomNivelPassivosSobreAtivos: Boolean,
    val nomeEmpresa: String? = null,
    val segmentoEmpresa: String? = null,
    val avaliacaoGeralDTO: AvaliacaoGeralDTO? = null
) {
    companion object {
        fun toEntity(dto: StockAnalysisDTO): StockAnalysisEntity {
            return StockAnalysisEntity(
                ticker = dto.ticker,
                estaEmSetorPerene = dto.estaEmSetorPerene,
                estaForaDeRecuperacaoJudicial = dto.estaForaDeRecuperacaoJudicial,
                //possuiBomNivelDeAcoesDisponiveisNoMercado = dto.possuiBomNivelDeAcoesDisponiveisNoMercado,
                possuiBomNivelRetornoSobrePatrimonio = dto.possuiBomNivelRetornoSobrePatrimonio,
                possuiBomNivelCrescimentoLucroNosUltimos5Anos = dto.possuiBomNivelCrescimentoLucroNosUltimos5Anos,
                possuiBomNivelMargemLiquida = dto.possuiBomNivelMargemLiquida,
                possuiBomNivelMargemEbit = dto.possuiBomNivelMargemEbit,
                possuiBomNivelDeCapacidadeDeQuitarDividaNoCurtoPrazo = dto.possuiBomNivelDeCapacidadeDeQuitarDividaNoCurtoPrazo,
                possuiBomNivelDividaLiquidaSobrePatrimonioLiquido = dto.possuiBomNivelDividaLiquidaSobrePatrimonioLiquido,
                //possuiBomNivelDividaLiquidaSobreResultadoOperacional = dto.possuiBomNivelDividaLiquidaSobreResultadoOperacional,
                possuiBomPrecoEmRelacaoAoLucroAssimComoValorPatrimonial = dto.possuiBomPrecoEmRelacaoAoLucroAssimComoValorPatrimonial,
                //possuiDireitoDeVendaDeAcoesIgualAoAcionistaControlador = dto.possuiDireitoDeVendaDeAcoesIgualAoAcionistaControlador,
                possuiNegociacaoAtiva = dto.possuiBomNivelPassivosSobreAtivos,
                nomeEmpresa = dto.nomeEmpresa,
                segmentoEmpresa = dto.segmentoEmpresa,
                avaliacaoGeralDTO = dto.avaliacaoGeralDTO
            )
        }
    }

    override fun toString(): String{
        return "StockAnalysisDTO(\"ticker\":\"${ticker}\",\"estaEmSetorPerene\":${estaEmSetorPerene},\"estaForaDeRecuperacaoJudicial\":${estaForaDeRecuperacaoJudicial},\"possuiBomNivelRetornoSobrePatrimonio\":${possuiBomNivelRetornoSobrePatrimonio},\"possuiBomNivelCrescimentoLucroNosUltimos5Anos\":${possuiBomNivelCrescimentoLucroNosUltimos5Anos},\"possuiBomNivelMargemLiquida\":${possuiBomNivelMargemLiquida},\"possuiBomNivelDeCapacidadeDeQuitarDividaNoCurtoPrazo\":${possuiBomNivelDeCapacidadeDeQuitarDividaNoCurtoPrazo},\"possuiBomNivelDividaLiquidaSobrePatrimonioLiquido\":${possuiBomNivelDividaLiquidaSobrePatrimonioLiquido},\"possuiBomPrecoEmRelacaoAoLucroAssimComoValorPatrimonial\":${possuiBomPrecoEmRelacaoAoLucroAssimComoValorPatrimonial},\"nomeEmpresa\":\"${nomeEmpresa}\",\"segmentoEmpresa\":\"${segmentoEmpresa}\",\"avaliacaoGeralDTO\":\"${avaliacaoGeralDTO.toString()}\")"
    }
}