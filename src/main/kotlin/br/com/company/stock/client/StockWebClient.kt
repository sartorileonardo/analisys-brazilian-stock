package br.com.company.stock.client

import br.com.company.stock.exception.ConnectionFailException
import br.com.company.stock.exception.NotFoundException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.retry.RetryCallback
import org.springframework.retry.support.RetryTemplate
import java.net.ConnectException
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration
import java.util.*

private const val MESSAGE_NOT_FOUND = "Sorry, the ticker not found!"
private const val MESSAGE_CONNECTION_FAIL = "Sorry, the connection whith external API is fail. Retry again after 1 minute!"

class StockWebClient {

    companion object {
        fun getResponse(url: String, ticker: String, timeoutMinutes: Long): HttpResponse<String> {
            val completeUrl = "${url}${ticker.toLowerCase()}/"
            val httpClient: HttpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build()
            val httpRequest: HttpRequest = HttpRequest.newBuilder()
                .uri(URI(completeUrl))
                .version(HttpClient.Version.HTTP_2)
                .GET()
                .timeout(Duration.ofMinutes(timeoutMinutes))
                .build()
            val retry = RetryTemplate.builder()
                .maxAttempts(3)
                .uniformRandomBackoff(3000, 6000)
                .build()
            val httpResponse = retry.execute(RetryCallback<HttpResponse<String>, java.net.ConnectException> {
                try{
                    httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString())
                }catch (e: ConnectException){
                    throw ConnectionFailException(MESSAGE_CONNECTION_FAIL)
                }
            })
            when(httpResponse.statusCode()){
                500 -> throw ConnectionFailException(MESSAGE_CONNECTION_FAIL)
                404 -> throw NotFoundException(MESSAGE_NOT_FOUND)
            }
            return httpResponse!!
        }

        fun getContentFromAPI(url: String, timeoutMinutes: Long, ticker: String): Map<String, Objects> {
            val props = ObjectMapper().readValue(
                getResponse(url, ticker, timeoutMinutes)
                    .body().substringAfter("<script id=\"__NEXT_DATA__\" type=\"application/json\">", "</script>"), Map::class.java).get("props") as Map<String, Objects>
            val pageProps = props["pageProps"] as Map<String, Objects>
            return pageProps
        }

    }

}
