package br.com.company.stock.mapper

import br.com.company.stock.dto.StockAnalisysDTO
import br.com.company.stock.entity.StockEntity
import org.springframework.stereotype.Component

@Component
class StockMapper {
    fun toEntity(dto: StockAnalisysDTO): StockEntity {
        return StockEntity(
            ticker = dto.ticker,
            estaEmSetorPerene = dto.estaEmSetorPerene,
            estaForaDeRecuperacaoJudicial = dto.estaForaDeRecuperacaoJudicial,
            possuiBomNivelRetornoSobrePatrimonio = dto.possuiBomNivelRetornoSobrePatrimonio,
            possuiBomNivelCrescimentoLucroNosUltimos5Anos = dto.possuiBomNivelCrescimentoLucroNosUltimos5Anos,
            possuiBomNivelMargemLiquida = dto.possuiBomNivelMargemLiquida,
            possuiBomNivelMargemEbit = dto.possuiBomNivelMargemEbit,
            possuiBomNivelDeCapacidadeDeQuitarDividaNoCurtoPrazo = dto.possuiBomNivelDeCapacidadeDeQuitarDividaNoCurtoPrazo,
            possuiBomNivelDividaLiquidaSobrePatrimonioLiquido = dto.possuiBomNivelDividaLiquidaSobrePatrimonioLiquido,
            possuiBomPrecoEmRelacaoAoValorPatrimonial = dto.possuiBomPrecoEmRelacaoAoValorPatrimonial,
            possuiNegociacaoAtiva = dto.possuiBomNivelPassivosSobreAtivos,
            nomeEmpresa = dto.nomeEmpresa,
            segmentoEmpresa = dto.segmentoEmpresa,
            score = dto.score
        )
    }

    fun toDTO(entity: StockEntity): StockAnalisysDTO {
        return StockAnalisysDTO(
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
            score = entity.score
        )
    }

}