package br.com.company.stock.client

import br.com.company.stock.exception.ConnectionFailException
import br.com.company.stock.exception.NotFoundException
import com.fasterxml.jackson.databind.ObjectMapper
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration
import java.util.*

class StockWebClient {

    companion object {
        fun getResponse(urlSite: String, timeoutMinutes: Long): HttpResponse<String> {
            val httpClient: HttpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build()
            val httpRequest: HttpRequest = HttpRequest.newBuilder()
                .uri(URI(urlSite))
                .version(HttpClient.Version.HTTP_2)
                .GET()
                .timeout(Duration.ofMinutes(timeoutMinutes))
                .build()
            val httpResponse: HttpResponse<String> = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString())
            when(httpResponse.statusCode()){
                500 -> throw ConnectionFailException("Sorry, the connection is fail. Retry again after 1 minute!")
                404 -> throw NotFoundException("Sorry, ticker not found!")
            }
            return httpResponse
        }

        fun getContentFromAPI(url: String, timeoutMinutes: Long, ticker: String): Map<String, Objects> {
            val urlApiExterna = "${url}${ticker.toLowerCase()}/"
            val props = ObjectMapper().readValue(
                getResponse(urlApiExterna, timeoutMinutes)
                    .body().substringAfter("<script id=\"__NEXT_DATA__\" type=\"application/json\">", "</script>"), Map::class.java).get("props") as Map<String, Objects>
            val pageProps = props["pageProps"] as Map<String, Objects>
            return pageProps
        }

    }

}
