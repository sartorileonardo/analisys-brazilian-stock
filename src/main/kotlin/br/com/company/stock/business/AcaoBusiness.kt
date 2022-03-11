package br.com.company.stock.business

import br.com.company.stock.client.AcaoWebClient
import br.com.company.stock.config.AcaoConfig
import br.com.company.stock.dto.AnaliseAcaoDto
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.util.*

@Component
class AcaoBusiness(val acaoConfig: AcaoConfig) {

    fun getAnalise(ticker: String): Mono<AnaliseAcaoDto> {

        val pageProps = AcaoWebClient.getContentFromAPI(ticker)

        val indicatorsTocker = pageProps["indicatorsTicker"] as Map<String, Objects>
        val precoSobreValorPatrimonial = if(indicatorsTocker.get("pvp").toString().equals("-")) 0.00 else indicatorsTocker.get("pvp").toString().replace(",", ".").replace("%", "").toDouble()
        val precoSobreLucro = if(indicatorsTocker.get("preco_lucro").toString().equals("-")) 0.00 else indicatorsTocker.get("preco_lucro").toString().replace(",", ".").replace("%", "").toDouble()

        val company = pageProps.get("company") as Map<String, Objects>

        val freeFloat = if(company.get("percentual_AcoesFreeFloat").toString().equals("-")) 0.00 else company.get("percentual_AcoesFreeFloat").toString().replace(",", ".").replace("%", "").toDouble()
        val margemLiquida = company.get("margemLiquida").toString().replace(",", ".").toDouble()
        val roe = company.get("roe").toString().replace(",", ".").toDouble()
        val cagrLucro = if(company.get("lucros_Cagr5").toString().equals("-")) 0.00 else company.get("lucros_Cagr5").toString().replace(",", ".").toDouble()
        val setorAtuacaoClean = company.get("setor_Atuacao_clean").toString()
        val estaEmRecuperacaoJudicial = company.get("injudicialProcess") as Boolean
        val liquidezCorrente = if(company.get("liquidezCorrente").toString().equals("-")) 0.00 else company.get("liquidezCorrente").toString().replace(",", ".").toDouble()
        val dividaLiquidaSobrePatrimonioLiquido = if(company.get("dividaliquida_PatrimonioLiquido").toString().equals("-")) 0.00 else company.get("dividaliquida_PatrimonioLiquido").toString().replace(",", ".").toDouble()
        val dividaLiquidaSobreEbitda = if(company.get("dividaLiquida_Ebit").toString().equals("-")) 0.00 else company.get("dividaLiquida_Ebit").toString().replace(",", ".").toDouble()

        return AnaliseAcaoDto(
            estaEmSetorPerene(setorAtuacaoClean),
            estaForaDeRecuperacaoJudicial(estaEmRecuperacaoJudicial),
            possuiBomNivelFreeFloat(freeFloat),
            possuiBomNivelROE(roe),
            possuiBomNivelCAGRLucro(cagrLucro),
            possuiBomNivelMargemLiquida(margemLiquida),
            possuiBomNivelLiquidez(liquidezCorrente),
            possuiBomNivelDividaLiquidaSobrePatrimonioLiquido(dividaLiquidaSobrePatrimonioLiquido),
            possuiBomNivelDividaLiquidaSobreEbitda(dividaLiquidaSobreEbitda),
            possuiBomPrecoEmRelacaoAoLucroAssimComoValorPatrimonial(precoSobreLucro, precoSobreValorPatrimonial)
        ).toMono()
    }

    fun possuiBomNivelFreeFloat(freeFloat: Double): Boolean = freeFloat.compareTo(acaoConfig.minimoFreeFloat.toDouble()) >= 1

    fun possuiBomNivelCAGRLucro(cagr: Double): Boolean = cagr.compareTo(acaoConfig.minimoCagrLucro5anos.toDouble()) >= 1

    fun possuiBomNivelROE(roe: Double): Boolean = roe.compareTo(acaoConfig.minimoROE.toDouble()) >= 1

    fun estaEmSetorPerene(setorDeOperacao: String): Boolean = arrayListOf("utilidade-publica", "materiais-basicos", "saude", "financeiro-e-outros").contains(setorDeOperacao)

    fun possuiBomNivelMargemLiquida(margemLiquida: Double): Boolean = margemLiquida.compareTo(acaoConfig.minimoMargemLiquida.toDouble()) >= 1

    fun possuiBomNivelDividaLiquidaSobrePatrimonioLiquido(dividaLiquidaSobrePatrimonioLiquido: Double): Boolean = dividaLiquidaSobrePatrimonioLiquido.compareTo(0.00) >= 1 && dividaLiquidaSobrePatrimonioLiquido.compareTo(acaoConfig.maximoDividaLiquidaSobrePatrimonioLiquido.toDouble()) < 1

    fun possuiBomNivelDividaLiquidaSobreEbitda(dividaLiquidaSobreEbitda: Double): Boolean = dividaLiquidaSobreEbitda.compareTo(0.00) >= 1 &&  dividaLiquidaSobreEbitda.compareTo(acaoConfig.maximoDividaLiquidaSobreEbitda.toDouble()) <= 1

    fun estaForaDeRecuperacaoJudicial(estaEmRecuperacaoJudicial: Boolean) = !estaEmRecuperacaoJudicial

    fun possuiBomNivelLiquidez(liquidezCorrente: Double): Boolean = liquidezCorrente.compareTo(1.00) >= 1

    fun possuiBomPrecoEmRelacaoAoLucroAssimComoValorPatrimonial(precoSobreLucro: Double, precoSobreValorPatrimonial: Double): Boolean = possuiBomNivelPrecoSobreLucro(precoSobreLucro) && possuiBomNivelPrecoSobreValorPatrimonial(precoSobreValorPatrimonial)

    private fun possuiBomNivelPrecoSobreValorPatrimonial(precoSobreValorPatrimonial: Double): Boolean = precoSobreValorPatrimonial.compareTo(0.00) >= 1 && precoSobreValorPatrimonial in 0.10 .. acaoConfig.maximoPrecoSobreValorPatrimonial.toDouble()

    private fun possuiBomNivelPrecoSobreLucro(precoSobreLucro: Double): Boolean = precoSobreLucro.compareTo(0.00) >= 1 && precoSobreLucro in 0.10 .. acaoConfig.maximoPrecoSobreLucro.toDouble()

}