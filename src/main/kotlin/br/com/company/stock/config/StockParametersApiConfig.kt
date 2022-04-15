package br.com.company.stock.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "config")
class StockParametersApiConfig{
    lateinit var setoresParenes: List<String>
    lateinit var minimoFreeFloat: String
    lateinit var minimoROE: String
    lateinit var minimoCagrLucro5anos: String
    lateinit var minimoMargemLiquida: String
    lateinit var minimoLiquidez: String
    lateinit var maximoDividaLiquidaSobrePatrimonioLiquido: String
    lateinit var maximoDividaLiquidaSobreEbitda: String
    lateinit var maximoPrecoSobreLucro: String
    lateinit var maximoPrecoSobreValorPatrimonial: String
    lateinit var url: String
    lateinit var timeout: String
    lateinit var timeMinRetry: String
    lateinit var timeMaxRetry: String
    lateinit var maxAttempsRetry: String
    lateinit var messageTickerNotFound: String
    lateinit var messageConnectionFail: String
}