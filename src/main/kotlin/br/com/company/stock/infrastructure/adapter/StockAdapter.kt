package br.com.company.stock.infrastructure.adapter

import br.com.company.stock.infrastructure.adapter.client.StockWebClient
import br.com.company.stock.infrastructure.adapter.client.dto.ResponseDTO
import br.com.company.stock.infrastructure.config.StockParametersApiConfig
import br.com.company.stock.core.controller.dto.AvaliacaoGeral
import br.com.company.stock.core.controller.dto.StockDTO
import br.com.company.stock.core.mapper.StockMapper
import br.com.company.stock.core.port.StockPort
import br.com.company.stock.infrastructure.adapter.repository.StockRepository
import br.com.company.stock.core.validation.TickerValidation
import io.netty.util.internal.StringUtil
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import reactor.core.publisher.Mono

@Component
class StockAdapter(
    val acaoConfig: StockParametersApiConfig,
    val mapper: StockMapper,
    val repository: StockRepository
) : StockPort {
    companion object {
        var LOGGER: Logger? = LoggerFactory.getLogger(StockAdapter::class.java)
    }

    override fun getAnalisys(ticker: String): Mono<StockDTO> {
        TickerValidation().validarTicker(ticker.trim())

        if (repository.existsById(ticker).block()!!) {
            return repository.findById(ticker)
                .map {
                    it.avaliacaoGeral = getAvaliacaoGeral(it.toString())
                    mapper.toDTO(it)
                }
                .doOnSuccess { LOGGER?.info("Analysis from database performed successfully: $ticker") }
                .doOnError { LOGGER?.error("Analysis from database did find an error $ticker: \nCause: ${it.message} \nMessage: ${it.message}") }
        }

        val entity = mapper.toEntity(getExternalAnalisys(ticker))

        return repository.save(entity)
            .map {
                it.avaliacaoGeral = getAvaliacaoGeral(it.toString())
                mapper.toDTO(it)
            }
            .doOnSuccess { LOGGER?.info("Analysis from external API performed and save successfully: $ticker") }
            .doOnError { LOGGER?.error("Analysis from external API did find an error and don't save $ticker: \nCause: ${it.message} \nMessage: ${it.message}") }
    }

    /*
    @Scheduled(fixedRate = 604800000)
    fun getAnalisysToTickersMostTraded() {
        acaoConfig.tickersMostTraded.forEach { ticker -> this.getAnalisys(ticker) }
        LOGGER!!.info("Loaded tickers most traded...\n")
    }
     */

    @Scheduled(cron = "0 0 12 1 * ?")
    fun cleanRepository() {
        repository.deleteAll()
        LOGGER!!.info("Repository is cleaned...\n")
    }

    private fun getAvaliacaoGeral(analisyToString: String): AvaliacaoGeral {
        val positivesPoints = StringUtils.countOccurrencesOf(analisyToString, "true")
        val otimoRange = 8..10
        val bomRange = 6..7
        val regularRange = 3..5
        val ruimRange = 0..2

        when (positivesPoints) {
            in ruimRange -> return AvaliacaoGeral.RUIM
            in regularRange -> return AvaliacaoGeral.REGULAR
            in bomRange -> return AvaliacaoGeral.BOM
            in otimoRange -> return AvaliacaoGeral.OTIMO
            else -> return AvaliacaoGeral.INDEFINIDO
        }
    }

    fun getExternalAnalisys(ticker: String): StockDTO {
        val responseDTO = ResponseDTO.parseMapToDto(StockWebClient(acaoConfig, ticker).getContentFromAPI())
        val indicatorsTicker = responseDTO.indicatorsTicker
        val valuation = responseDTO.valuation
        val paper = responseDTO.paper
        val indicadoresAlternativos = paper?.indicadores
        val indicadorAlternativoPL = indicadoresAlternativos?.get(0)?.Value_F
        val indicadorAlternativoPVP = if(indicadoresAlternativos?.get(1)?.Value_F == null) extrairDouble(valuation?.pvp.toString()) else extrairDouble(indicadoresAlternativos.get(1).Value_F!!)
        val precoSobreValorPatrimonial = extrairDouble(listOfNotNull(indicatorsTicker?.pvp, indicadorAlternativoPVP, valuation?.pvp).distinct().first().toString())
        //val precoSobreLucro = extrairDouble(listOfNotNull(indicatorsTicker?.preco_lucro, indicadorAlternativoPL).distinct().first().toString())
        val company = responseDTO.company

        val margemLiquida = extrairDouble(company?.margemLiquida.toString())
        val roe = extrairDouble(company?.roe.toString())
        val cagrLucro = extrairDouble(company?.lucros_Cagr5.toString())
        val setorAtuacaoClean = company?.setor_Atuacao_clean.toString()
        val estaEmRecuperacaoJudicial = company?.injudicialProcess.toString().toBoolean()
        val liquidezCorrente = extrairDouble(company?.liquidezCorrente.toString())
        val dividaLiquidaSobrePatrimonioLiquido = extrairDouble(company?.dividaliquida_PatrimonioLiquido.toString())
        val passivosAtivos = extrairDouble(responseDTO.otherIndicators?.passivosAtivos.toString())
        val margemEbit = extrairDouble(responseDTO.otherIndicators?.margemEbit.toString())

        return StockDTO(
            ticker,
            estaEmSetorPerene(setorAtuacaoClean),
            estaForaDeRecuperacaoJudicial(estaEmRecuperacaoJudicial),
            possuiBomNivelRetornoSobrePatrimonio(roe),
            possuiBomNivelCrescimentoLucroNosUltimos5Anos(cagrLucro),
            possuiBomNivelMargemLiquida(margemLiquida),
            possuiBomNivelMargemEbit(margemEbit),
            possuiBomNivelLiquidezCorrente(liquidezCorrente),
            possuiBomNivelDividaLiquidaSobrePatrimonioLiquido(dividaLiquidaSobrePatrimonioLiquido),
            possuiBomPrecoEmRelacaoAoValorPatrimonial(precoSobreValorPatrimonial),
            possuiBomNivelPassivosSobreAtivos(passivosAtivos),
            company?.bookName,
            company?.segmento_Atuacao
        )

    }

    private fun possuiBomNivelMargemEbit(margemEbit: Double) = margemEbit.compareTo(acaoConfig.minimoMargemLiquida.toDouble()) >= 1
    private fun possuiBomNivelPassivosSobreAtivos(passivosAtivos: Double) = passivosAtivos.compareTo(1) <= 1

    private fun possuiBomNivelFreeFloat(freeFloat: Double) =
        freeFloat.compareTo(acaoConfig.minimoFreeFloat.toDouble()) >= 1

    private fun possuiBomNivelCrescimentoLucroNosUltimos5Anos(cagr: Double) =
        cagr.compareTo(acaoConfig.minimoCagrLucro5anos.toDouble()) >= 1

    private fun possuiBomNivelRetornoSobrePatrimonio(roe: Double) = roe.compareTo(acaoConfig.minimoROE.toDouble()) >= 1

    private fun estaEmSetorPerene(setorDeOperacao: String) = acaoConfig.setoresParenes.contains(setorDeOperacao)

    private fun possuiBomNivelMargemLiquida(margemLiquida: Double) =
        margemLiquida.compareTo(acaoConfig.minimoMargemLiquida.toDouble()) >= 1

    private fun possuiBomNivelDividaLiquidaSobrePatrimonioLiquido(dividaLiquidaSobrePatrimonioLiquido: Double) =
        dividaLiquidaSobrePatrimonioLiquido.compareTo(acaoConfig.maximoDividaLiquidaSobrePatrimonioLiquido.toDouble()) < 1

    private fun possuiBomNivelDividaLiquidaSobreEbitda(dividaLiquidaSobreEbitda: Double) =
        dividaLiquidaSobreEbitda.compareTo(0.00) >= 1 && dividaLiquidaSobreEbitda.compareTo(acaoConfig.maximoDividaLiquidaSobreEbitda.toDouble()) <= 1

    private fun estaForaDeRecuperacaoJudicial(estaEmRecuperacaoJudicial: Boolean) = !estaEmRecuperacaoJudicial

    private fun possuiBomNivelLiquidezCorrente(liquidezCorrente: Double) =
        liquidezCorrente.compareTo(1.00) >= acaoConfig.minimoLiquidez.toInt()

    private fun possuiBomPrecoEmRelacaoAoValorPatrimonial(precoSobreValorPatrimonial: Double) = possuiBomNivelPrecoSobreValorPatrimonial(precoSobreValorPatrimonial)

    private fun possuiBomNivelPrecoSobreValorPatrimonial(precoSobreValorPatrimonial: Double) =
        precoSobreValorPatrimonial.compareTo(0.00) >= 1 && precoSobreValorPatrimonial in 0.10..acaoConfig.maximoPrecoSobreValorPatrimonial.toDouble()

    private fun possuiBomNivelPrecoSobreLucro(precoSobreLucro: Double) =
        precoSobreLucro.compareTo(0.00) >= 1 && precoSobreLucro in 0.10..acaoConfig.maximoPrecoSobreLucro.toDouble()

    private fun possuiDireitoDeVendaDeAcoesIgualAoAcionistaControlador(tagAlong: Double) = tagAlong.toInt() == 100

    private fun extrairDouble(texto: String): Double =
        if (StringUtil.isNullOrEmpty(texto) || texto == "-") 0.00 else requireNotNull(texto).trim()
            .replace(",", ".")
            .replace("%", "")
            .toDouble()
}