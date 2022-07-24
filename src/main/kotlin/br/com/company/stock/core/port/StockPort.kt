package br.com.company.stock.core.port

import br.com.company.stock.core.controller.dto.StockDTO
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
interface StockPort {
    fun getAnalisys(ticker: String): Mono<StockDTO>
}