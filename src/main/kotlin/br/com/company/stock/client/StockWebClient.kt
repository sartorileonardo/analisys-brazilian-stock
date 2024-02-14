package br.com.company.stock.client

import br.com.company.stock.config.StockParametersConfig
import br.com.company.stock.exception.ConnectionFailException
import br.com.company.stock.exception.NotFoundException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.client.WebClient
import java.time.Duration
import java.util.*
import java.util.function.Function


class StockWebClient(
    private val config: StockParametersConfig
) {

    private fun getResponseByExternalAPI(ticker: String): String? {
        val completeUrl = "${config.urlExternalAPI}${ticker.toLowerCase()}/"
        val webClient =
            WebClient.create().mutate().codecs { it.defaultCodecs().maxInMemorySize(16 * 1024 * 1024) }.build()
        val responseJson = getResponse(webClient, completeUrl)
        return responseJson
    }

    private fun getResponse(
        webClient: WebClient,
        completeUrl: String
    ) = webClient
        .get()
        .uri(completeUrl)
        .retrieve()
        .onStatus(HttpStatus::is4xxClientError, Function { throw NotFoundException(config.messageTickerNotFound) })
        .onStatus(
            HttpStatus::is5xxServerError,
            Function { throw ConnectionFailException(config.messageConnectionFail) })
        .bodyToMono(String::class.java)
        .retry(config.timeMaxRetry.toLong())
        .timeout(Duration.ofMillis(config.timeoutExternalAPI.toLong()))
        .block()

    fun getContentFromAPI(ticker: String): Map<String, Objects> {
        val props = ObjectMapper().readValue(
            getResponseByExternalAPI(ticker)!!.substringAfter(
                "<script id=\"__NEXT_DATA__\" type=\"application/json\">",
                "</script>"
            ),
            Map::class.java
        ).get("props") as Map<String, Objects>
        return props["pageProps"] as Map<String, Objects>
    }

    fun getContentBySecondExternalAPI(ticker: String): Map<String, Objects> {
        val props = ObjectMapper().readValue(
            getResponseByExternalAPI(ticker)!!,
            Map::class.java
        ).get("list") as Map<String, Objects>
        return props
    }

}
