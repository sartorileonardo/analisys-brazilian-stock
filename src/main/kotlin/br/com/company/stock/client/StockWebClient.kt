package br.com.company.stock.client

import br.com.company.stock.client.dto.ResponseDTO
import br.com.company.stock.config.StockParametersApiConfig
import br.com.company.stock.exception.ConnectionFailException
import br.com.company.stock.exception.NotFoundException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.retry.RetryCallback
import org.springframework.retry.support.RetryTemplate
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.net.ConnectException
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration
import java.util.*

class StockWebClient(
    private val config: StockParametersApiConfig,
    private val ticker: String
) {
    companion object {
        val MESSAGE_CONNECTION_FAIL = "Sorry, connection fail with external API, retry later."
        val MESSAGE_NOT_FOUND = "Sorry, ticker not found."
    }
        fun getResponse(): HttpResponse<String>? {
            val completeUrl = "${config.url}${ticker.toLowerCase()}/"
            val httpClient: HttpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build()
            val httpRequest: HttpRequest = getHttpRequest(completeUrl, config.timeout.toLong())
            val retry = getRetryConfig()
            val httpResponse = getHttpResponse(httpRequest, httpClient, retry)
            return httpResponse
        }

        fun getResponseReactive(): Mono<ResponseDTO>? {
            val completeUrl = "${config.url}${ticker.toLowerCase()}/"
            val webClient = WebClient.create(completeUrl)
            return webClient
                .get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError){
                    Mono.error { NotFoundException(MESSAGE_NOT_FOUND) }
                }
                .onStatus(HttpStatus::is5xxServerError){
                    Mono.error { ConnectionFailException(MESSAGE_CONNECTION_FAIL) }
                }
                .bodyToMono(ResponseDTO::class.java)
        }

        private fun getHttpResponse(httpRequest: HttpRequest, httpClient: HttpClient, retry: RetryTemplate?): HttpResponse<String>? {
            val httpResponse = retry?.execute(RetryCallback<HttpResponse<String>, java.net.ConnectException> {
                try{
                    httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString())
                }catch (e: ConnectException){
                    throw ConnectionFailException(config.messageConnectionFail)
                }
            })
            when(httpResponse?.statusCode()){
                500 -> throw ConnectionFailException(config.messageConnectionFail)
                404 -> throw NotFoundException(config.messageTickerNotFound)
            }
            return httpResponse
        }

        private fun getRetryConfig() = RetryTemplate.builder()
            .maxAttempts(config.maxAttempsRetry.toInt())
            .uniformRandomBackoff(config.timeMinRetry.toLong(), config.timeMaxRetry.toLong())
            .build()

        private fun getHttpRequest(completeUrl: String, timeoutMinutes: Long) = HttpRequest.newBuilder()
            .uri(URI(completeUrl))
            .version(HttpClient.Version.HTTP_2)
            .GET()
            .timeout(Duration.ofMinutes(timeoutMinutes))
            .build()

        fun getContentFromAPI(): Map<String, Objects> {
            val props = ObjectMapper().readValue(getResponse()
                    ?.body()!!.substringAfter("<script id=\"__NEXT_DATA__\" type=\"application/json\">", "</script>"), Map::class.java).get("props") as Map<String, Objects>
            return props["pageProps"] as Map<String, Objects>
        }

}
