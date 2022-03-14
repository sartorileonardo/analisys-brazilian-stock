package br.com.company.stock.business

import br.com.company.stock.client.StockWebClient
import br.com.company.stock.config.StockParametersApiConfig
import br.com.company.stock.dto.StockAnalysisDto
import br.com.company.stock.exception.BusinessException
import io.netty.util.internal.StringUtil
import org.springframework.stereotype.Component
import org.springframework.util.ObjectUtils
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.util.*

@Component
class StockBusiness(val acaoConfig: StockParametersApiConfig) {

    fun getAnalise(ticker: String): Mono<StockAnalysisDto> {

        validarTicker(ticker)

        val pageProps = StockWebClient.getContentFromAPI(acaoConfig.url, acaoConfig.timeout.toLong(), ticker)

        //TODO: Adicionar segurança ao cast de map, para realizar o cast somente quando o map tiver a chave e quando não for null seu valor
        val indicatorsTicker = pageProps["indicatorsTicker"] as Map<String, Objects>

        val precoSobreValorPatrimonial = extrairDouble(indicatorsTicker.get("pvp").toString())
        val precoSobreLucro = extrairDouble(indicatorsTicker.get("preco_lucro").toString())

        //TODO: Adicionar segurança ao cast de map, para realizar o cast somente quando o map tiver a chave e quando não for null seu valor
        val company = pageProps.get("company") as Map<String, Objects>

        val freeFloat = extrairDouble(company.get("percentual_AcoesFreeFloat").toString())
        val margemLiquida = extrairDouble(company.get("margemLiquida").toString())
        val roe = extrairDouble(company.get("roe").toString())
        val cagrLucro = extrairDouble(company.get("lucros_Cagr5").toString())
        val setorAtuacaoClean = company.get("setor_Atuacao_clean").toString()
        val estaEmRecuperacaoJudicial = company.get("injudicialProcess").toString().toBoolean()
        val liquidezCorrente = extrairDouble(company.get("liquidezCorrente").toString())
        val dividaLiquidaSobrePatrimonioLiquido = extrairDouble(company.get("dividaliquida_PatrimonioLiquido").toString())
        val dividaLiquidaSobreEbitda = extrairDouble(company.get("dividaLiquida_Ebit").toString())

        return StockAnalysisDto(
            estaEmSetorPerene(setorAtuacaoClean),
            estaForaDeRecuperacaoJudicial(estaEmRecuperacaoJudicial),
            possuiBomNivelFreeFloat(freeFloat),
            possuiBomNivelRetornoSobrePatrimonio(roe),
            possuiBomNivelCrescimentoLucroNosUltimos5Anos(cagrLucro),
            possuiBomNivelMargemLiquida(margemLiquida),
            possuiBomNivelLiquidez(liquidezCorrente),
            possuiBomNivelDividaLiquidaSobrePatrimonioLiquido(dividaLiquidaSobrePatrimonioLiquido),
            possuiBomNivelDividaLiquidaSobreEbitda(dividaLiquidaSobreEbitda),
            possuiBomPrecoEmRelacaoAoLucroAssimComoValorPatrimonial(precoSobreLucro, precoSobreValorPatrimonial)
        ).toMono()
    }

    private fun validarTicker(ticker: String) {
        if(ObjectUtils.isEmpty(ticker)){
            throw BusinessException("Ticker é obrigatório!")
        }
        if(ticker.trim().length < 5 || ticker.trim().length > 6){
            throw BusinessException("Ticker deve ter entre 5 e 6 caracteres!")
        }
        if(ticker.trim().matches(Regex("\\d+"))){
            throw BusinessException("Ticker não pode ser somente numeros!")
        }
        if(ticker.trim().matches(Regex("^[a-zA-Z]+\$"))){
            throw BusinessException("Ticker não pode ser somente letras!")
        }
        //TODO: Adicionar validação para aceitar somente ticker com final 3, 4 e 11
    }

    private fun possuiBomNivelFreeFloat(freeFloat: Double): Boolean = freeFloat.compareTo(acaoConfig.minimoFreeFloat.toDouble()) >= 1

    private fun possuiBomNivelCrescimentoLucroNosUltimos5Anos(cagr: Double): Boolean = cagr.compareTo(acaoConfig.minimoCagrLucro5anos.toDouble()) >= 1

    private fun possuiBomNivelRetornoSobrePatrimonio(roe: Double): Boolean = roe.compareTo(acaoConfig.minimoROE.toDouble()) >= 1

    private fun estaEmSetorPerene(setorDeOperacao: String): Boolean = arrayListOf("utilidade-publica", "materiais-basicos", "saude", "financeiro-e-outros").contains(setorDeOperacao)

    private fun possuiBomNivelMargemLiquida(margemLiquida: Double): Boolean = margemLiquida.compareTo(acaoConfig.minimoMargemLiquida.toDouble()) >= 1

    private fun possuiBomNivelDividaLiquidaSobrePatrimonioLiquido(dividaLiquidaSobrePatrimonioLiquido: Double): Boolean = dividaLiquidaSobrePatrimonioLiquido.compareTo(0.00) >= 1 && dividaLiquidaSobrePatrimonioLiquido.compareTo(acaoConfig.maximoDividaLiquidaSobrePatrimonioLiquido.toDouble()) < 1

    private fun possuiBomNivelDividaLiquidaSobreEbitda(dividaLiquidaSobreEbitda: Double): Boolean = dividaLiquidaSobreEbitda.compareTo(0.00) >= 1 &&  dividaLiquidaSobreEbitda.compareTo(acaoConfig.maximoDividaLiquidaSobreEbitda.toDouble()) <= 1

    private fun estaForaDeRecuperacaoJudicial(estaEmRecuperacaoJudicial: Boolean) = !estaEmRecuperacaoJudicial

    private fun possuiBomNivelLiquidez(liquidezCorrente: Double): Boolean = liquidezCorrente.compareTo(1.00) >= 1

    private fun possuiBomPrecoEmRelacaoAoLucroAssimComoValorPatrimonial(precoSobreLucro: Double, precoSobreValorPatrimonial: Double): Boolean = possuiBomNivelPrecoSobreLucro(precoSobreLucro) && possuiBomNivelPrecoSobreValorPatrimonial(precoSobreValorPatrimonial)

    private fun possuiBomNivelPrecoSobreValorPatrimonial(precoSobreValorPatrimonial: Double): Boolean = precoSobreValorPatrimonial.compareTo(0.00) >= 1 && precoSobreValorPatrimonial in 0.10 .. acaoConfig.maximoPrecoSobreValorPatrimonial.toDouble()

    private fun possuiBomNivelPrecoSobreLucro(precoSobreLucro: Double): Boolean = precoSobreLucro.compareTo(0.00) >= 1 && precoSobreLucro in 0.10 .. acaoConfig.maximoPrecoSobreLucro.toDouble()

    private fun extrairDouble(texto: String): Double = if(!StringUtil.isNullOrEmpty(texto) || texto.equals("-")) 0.00 else texto.trim().replace(",", ".").replace("%", "").toDouble()

}