package br.com.company.stock.client

import br.com.company.stock.config.StockParametersApiConfig
import br.com.company.stock.exception.NotFoundException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.client.WebClient
import java.time.Duration
import java.util.*
import java.util.function.Function

class StockWebClient(
    private val config: StockParametersApiConfig
) {

    private fun getResponse(ticker: String): String? {
        val completeUrl = "${config.urlExternalAPI}${ticker.toLowerCase()}/"
        val webClient =
            WebClient.create().mutate().codecs { it -> it.defaultCodecs().maxInMemorySize(16 * 1024 * 1024) }.build()
        val responseJson = webClient
            .get()
            .uri(completeUrl)
            .retrieve()
            .onStatus(HttpStatus::is4xxClientError, Function { throw NotFoundException(config.messageTickerNotFound) })
            .onStatus(HttpStatus::is5xxServerError, Function { throw NotFoundException(config.messageConnectionFail) })
            .bodyToMono(String::class.java)
            .retry(config.timeMaxRetry.toLong())
            .timeout(Duration.ofMillis(config.timeoutExternalAPI.toLong()))
            .block()
        return responseJson
    }

    fun getContentFromAPI(ticker: String): Map<String, Objects> {
        val props = ObjectMapper().readValue(
            getResponse(ticker)!!.substringAfter(
                "<script id=\"__NEXT_DATA__\" type=\"application/json\">",
                "</script>"
            ),
            Map::class.java
        ).get("props") as Map<String, Objects>
        return props["pageProps"] as Map<String, Objects>
    }

}
