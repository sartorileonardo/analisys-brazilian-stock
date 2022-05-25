package br.com.company.stock.service

import br.com.company.stock.client.StockWebClient
import br.com.company.stock.client.dto.ResponseDTO
import br.com.company.stock.config.StockParametersApiConfig
import br.com.company.stock.controller.dto.StockAnalysisDto
import br.com.company.stock.validation.TickerValidation
import io.netty.util.internal.StringUtil
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@CacheConfig(cacheNames = ["analise"])
@Service
class StockService(val acaoConfig: StockParametersApiConfig) {

    companion object{
        var logger: Logger? = LoggerFactory.getLogger(StockService::class.java)
    }
    @Cacheable(value = ["analise"])
    fun getAnalisys(ticker: String): Mono<StockAnalysisDto> {

        TickerValidation().validarTicker(ticker.trim())

        val responseDTO = ResponseDTO.parseMapToDto(StockWebClient(acaoConfig, ticker).getContentFromAPI())
        val indicatorsTicker = responseDTO?.indicatorsTicker
        val valuation = responseDTO?.valuation
        val paper = responseDTO?.paper
        val indicadoresAlternativos = paper?.indicadores
        val indicadorAlternativoPL = indicadoresAlternativos?.get(0)?.Value_F
        val indicadorAlternativoPVP = indicadoresAlternativos?.get(1)?.Value_F
        val precoSobreValorPatrimonial = if(indicatorsTicker?.pvp == null) extrairDouble(indicadorAlternativoPVP.toString()) else extrairDouble(valuation?.pvp.toString())
        val precoSobreLucro = if(indicatorsTicker?.preco_lucro == null) extrairDouble(indicadorAlternativoPL.toString()) else extrairDouble(indicatorsTicker.preco_lucro.toString())
        val company = responseDTO?.company

        val freeFloat = extrairDouble(company?.percentual_AcoesFreeFloat.toString())
        val margemLiquida = extrairDouble(company?.margemLiquida.toString())
        val roe = extrairDouble(company?.roe.toString())
        val cagrLucro = extrairDouble(company?.lucros_Cagr5.toString())
        val setorAtuacaoClean = company?.setor_Atuacao_clean.toString()
        val estaEmRecuperacaoJudicial = company?.injudicialProcess.toString().toBoolean()
        val liquidezCorrente = extrairDouble(company?.liquidezCorrente.toString())
        val dividaLiquidaSobrePatrimonioLiquido = extrairDouble(company?.dividaliquida_PatrimonioLiquido.toString())
        val dividaLiquidaSobreEbitda = extrairDouble(company?.dividaLiquida_Ebit.toString())

        return Mono.justOrEmpty(
            StockAnalysisDto(
                estaEmSetorPerene(setorAtuacaoClean),
                estaForaDeRecuperacaoJudicial(estaEmRecuperacaoJudicial),
                possuiBomNivelFreeFloat(freeFloat),
                possuiBomNivelRetornoSobrePatrimonio(roe),
                possuiBomNivelCrescimentoLucroNosUltimos5Anos(cagrLucro),
                possuiBomNivelMargemLiquida(margemLiquida),
                possuiBomNivelLiquidezCorrente(liquidezCorrente),
                possuiBomNivelDividaLiquidaSobrePatrimonioLiquido(dividaLiquidaSobrePatrimonioLiquido),
                possuiBomNivelDividaLiquidaSobreEbitda(dividaLiquidaSobreEbitda),
                possuiBomPrecoEmRelacaoAoLucroAssimComoValorPatrimonial(precoSobreLucro, precoSobreValorPatrimonial)
            )
        )
            .doOnSuccess{ logger?.info("Analysis performed successfully. $ticker") }
            .doOnError{ logger?.error("An error occurred while performing analysis $ticker: \nCause: ${it.message} \nMessage: ${it.message}") }
    }

    private fun possuiBomNivelFreeFloat(freeFloat: Double) = freeFloat.compareTo(acaoConfig.minimoFreeFloat.toDouble()) >= 1

    private fun possuiBomNivelCrescimentoLucroNosUltimos5Anos(cagr: Double) = cagr.compareTo(acaoConfig.minimoCagrLucro5anos.toDouble()) >= 1

    private fun possuiBomNivelRetornoSobrePatrimonio(roe: Double) = roe.compareTo(acaoConfig.minimoROE.toDouble()) >= 1

    private fun estaEmSetorPerene(setorDeOperacao: String) = acaoConfig.setoresParenes.contains(setorDeOperacao)

    private fun possuiBomNivelMargemLiquida(margemLiquida: Double) = margemLiquida.compareTo(acaoConfig.minimoMargemLiquida.toDouble()) >= 1

    private fun possuiBomNivelDividaLiquidaSobrePatrimonioLiquido(dividaLiquidaSobrePatrimonioLiquido: Double) = dividaLiquidaSobrePatrimonioLiquido.compareTo(acaoConfig.maximoDividaLiquidaSobrePatrimonioLiquido.toDouble()) < 1

    private fun possuiBomNivelDividaLiquidaSobreEbitda(dividaLiquidaSobreEbitda: Double) = dividaLiquidaSobreEbitda.compareTo(0.00) >= 1 && dividaLiquidaSobreEbitda.compareTo(acaoConfig.maximoDividaLiquidaSobreEbitda.toDouble()) <= 1

    private fun estaForaDeRecuperacaoJudicial(estaEmRecuperacaoJudicial: Boolean) = !estaEmRecuperacaoJudicial

    private fun possuiBomNivelLiquidezCorrente(liquidezCorrente: Double) = liquidezCorrente.compareTo(1.00) >= 1

    private fun possuiBomPrecoEmRelacaoAoLucroAssimComoValorPatrimonial(precoSobreLucro: Double, precoSobreValorPatrimonial: Double) = possuiBomNivelPrecoSobreLucro(precoSobreLucro) && possuiBomNivelPrecoSobreValorPatrimonial(precoSobreValorPatrimonial)

    private fun possuiBomNivelPrecoSobreValorPatrimonial(precoSobreValorPatrimonial: Double) = precoSobreValorPatrimonial.compareTo(0.00) >= 1 && precoSobreValorPatrimonial in 0.10..acaoConfig.maximoPrecoSobreValorPatrimonial.toDouble()

    private fun possuiBomNivelPrecoSobreLucro(precoSobreLucro: Double) = precoSobreLucro.compareTo(0.00) >= 1 && precoSobreLucro in 0.10..acaoConfig.maximoPrecoSobreLucro.toDouble()

    private fun extrairDouble(texto: String): Double =
        if (StringUtil.isNullOrEmpty(texto) || texto == "-") 0.00 else texto.trim()
            .replace(",", ".")
            .replace("%", "")
            .toDouble()
}
