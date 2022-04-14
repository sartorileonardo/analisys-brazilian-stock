# spring-boot-brazilian-stock-analisys

> Spring boot with Kotlin, Spring Boot, Cache, WebClient, Swagger and Heroku Cloud

## Run Application
`mvn spring-boot:run`

## Swagger localhost documentation
http://localhost:8888/swagger-ui/index.html#

## Swagger heroku documentation
https://analisys-brazilian-stock.herokuapp.com/swagger-ui/index.html#

## Postman
><code>[postman/postman_collection.json](postman/postman_collection.json)</code>

## application.yml

```yaml
config:
  setoresParenes: [
    "utilidade-publica",
    "materiais-basicos",
    "financeiro-e-outros",
    "saude"]
  minimoFreeFloat: 25.00
  minimoROE: 10.00
  minimoCagrLucro5anos: 15.00
  minimoMargemLiquida: 10.00
  minimoLiquidez: 2.00
  maximoDividaLiquidaSobrePatrimonioLiquido: 2.00
  maximoDividaLiquidaSobreEbitda: 2.50
  maximoPrecoSobreLucro: 15.00
  maximoPrecoSobreValorPatrimonial: 3.00

  url: "${URL_EXTERNAL_API}"
  timeout: 1

server:
  port: 8888

```

## StockAnalisysDto.java
```kotlin
data class StockAnalysisDto(
    val estaEmSetorPerene: Boolean,
    val estaForaDeRecuperacaoJudicial: Boolean,
    val possuiBomNivelFreeFloat: Boolean,
    val possuiBomNivelRetornoSobrePatrimonio: Boolean,
    val possuiBomNivelCrescimentoLucroNosUltimos5Anos: Boolean,
    val possuiBomNivelMargemLiquida: Boolean,
    val possuiBomNivelLiquidezCorrente: Boolean,
    val possuiBomNivelDividaLiquidaSobrePatrimonioLiquido: Boolean,
    val possuiBomNivelDividaLiquidaSobreEbitda: Boolean,
    val possuiBomPrecoEmRelacaoAoLucroAssimComoValorPatrimonial: Boolean
)
```


## StockService.java
```kotlin
@CacheConfig(cacheNames = ["analise"])
@Service
class StockService(val acaoConfig: StockParametersApiConfig) {
    @Cacheable(value = ["analise"])
    fun getAnalisys(ticker: String): Mono<StockAnalysisDto> {

        TickerValidation().validarTicker(ticker)

        val pageProps = StockWebClient.getContentFromAPI(acaoConfig.url, acaoConfig.timeout.toLong(), ticker)
        val indicatorsTicker = pageProps.getOrDefault("indicatorsTicker", emptyMap<String, Objects>()) as Map<*, *>
        val precoSobreValorPatrimonial = extrairDouble(indicatorsTicker["pvp"].toString())
        val precoSobreLucro = extrairDouble(indicatorsTicker["preco_lucro"].toString())
        val company = pageProps.getOrDefault("company", emptyMap<String, Objects>()) as Map<*, *>

        val freeFloat = extrairDouble(company["percentual_AcoesFreeFloat"].toString())
        val margemLiquida = extrairDouble(company["margemLiquida"].toString())
        val roe = extrairDouble(company["roe"].toString())
        val cagrLucro = extrairDouble(company["lucros_Cagr5"].toString())
        val setorAtuacaoClean = company["setor_Atuacao_clean"].toString()
        val estaEmRecuperacaoJudicial = company["injudicialProcess"].toString().toBoolean()
        val liquidezCorrente = extrairDouble(company["liquidezCorrente"].toString())
        val dividaLiquidaSobrePatrimonioLiquido = extrairDouble(company["dividaliquida_PatrimonioLiquido"].toString())
        val dividaLiquidaSobreEbitda = extrairDouble(company["dividaLiquida_Ebit"].toString())

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
```

## StockController.java
```kotlin
@CrossOrigin(origins = ["*"])
@RestController
@RequestMapping("/stock")
@Api(description = "Analise de empresa brasileira de capital aberto")
class StockController @Autowired constructor(val service: StockService){
    @GetMapping("/analisys/{ticker}")
    fun getAnalisys(@PathVariable ticker: String) = service.getAnalisys(ticker)
}
```

## StockControllerTest.java
```kotlin
@SpringBootTest
@AutoConfigureMockMvc
class StockControllerTest(@Autowired val mockMvc: MockMvc) {
    private val urlValidApi: String = "/stock/analisys/"
    private val urlInvalidApi: String = "/stock/analisys/ticker"
    private val exampleValidTicker: String = "ABEV3"
    private val exampleInvalidTicker: String = "ABEVV"
    private val exampleNotFoundTicker: String = "ABEV5"


    @Test
    fun findAnalisysWhenReturnIs200Code() {
        mockMvc.perform(get("${urlValidApi}${exampleValidTicker}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
    }

    @Test
    fun findAnalisysWhenReturnIs400Code(){
        mockMvc.perform(get("${urlValidApi}${exampleInvalidTicker}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun findAnalisysWhenReturnIs404Code(){
        mockMvc.perform(get("${urlInvalidApi}${exampleValidTicker}")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound)
    }

}
```
## TickerValidation.java
```kotlin
class TickerValidation {
    fun validarTicker(ticker: String): Boolean {
        return validarSeForVazio(ticker)
            .and(validarSePossuiSomenteNumeros(ticker))
            .and(validarSePossuiSomenteNumeros(ticker))
            .and(validarSePossuiSomenteLetras(ticker))
            .and(validarSeTerminaComDigitoAceito(ticker))
            .and(validarSeForBDR(ticker))
    }

    private fun validarSeForBDR(ticker: String) = if(listOf("32", "33", "34", "35").contains(ticker.substring(ticker.length - 2))) throw BusinessException("O sistema não suporta ticker de BDR's, tente novamente com um ticker de empresa brasileira!") else true

    private fun validarSeTerminaComDigitoAceito(ticker: String) = if(!listOf("3", "4").contains(ticker.last().toString()) && ticker.substring(ticker.length - 2) != "11")
        throw BusinessException("Ticker deve terminar com 3, 4 ou 11, exemplo: ABEV3, BBDC4, TAEE11!") else true

    private fun validarSePossuiSomenteLetras(ticker: String) = if(ticker.matches(Regex("^[a-zA-Z]+\$"))) throw BusinessException("Ticker não pode ser somente letras!") else true

    private fun validarSePossuiSomenteNumeros(ticker: String) = if(ticker.matches(Regex("\\d+"))) throw BusinessException("Ticker não pode ser somente numeros!") else true

    private fun validarSeForVazio(ticker: String) = if(ticker.isNullOrBlank()) throw BusinessException("Ticker é obrigatório!") else true
}
```
