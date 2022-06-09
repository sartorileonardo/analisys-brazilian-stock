package br.com.company.stock.client

import br.com.company.stock.config.StockParametersApiConfig
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.web.reactive.function.client.WebClient
import java.time.Duration
import java.util.*

class StockWebClient(
    private val config: StockParametersApiConfig,
    private val ticker: String
) {

    fun getResponse(): String? {
        val completeUrl = "${config.urlExternalAPI}${ticker.toLowerCase()}/"
        val webClient = WebClient.create().mutate().codecs { it -> it.defaultCodecs().maxInMemorySize(16 * 1024 * 1024) }.build()
        val responseJson = webClient.get()
            .uri(completeUrl)
            .exchange()
            .retry(config.timeMaxRetry.toLong())
            .timeout(Duration.ofMillis(config.timeoutExternalAPI.toLong()))
            .block()!!
            .bodyToMono(String::class.java)
            .block()
        return responseJson
    }

    fun getContentFromAPI(): Map<String, Objects> {
        val props = ObjectMapper().readValue(
            getResponse()!!.substringAfter("<script id=\"__NEXT_DATA__\" type=\"application/json\">", "</script>"),
            Map::class.java
        ).get("props") as Map<String, Objects>
        return props["pageProps"] as Map<String, Objects>
    }

}
