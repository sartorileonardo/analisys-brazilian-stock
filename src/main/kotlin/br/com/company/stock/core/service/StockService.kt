package br.com.company.stock.core.service

import br.com.company.stock.core.controller.dto.StockDTO
import br.com.company.stock.core.port.StockPort
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class StockService(val port: StockPort) {
    @Cacheable(value = ["analisys"])
    fun getAnalisys(ticker: String): Mono<StockDTO> {
        return port.getAnalisys(ticker)
    }
}
