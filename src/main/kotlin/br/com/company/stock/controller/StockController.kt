package br.com.company.stock.controller

import br.com.company.stock.dto.StockAnalysisDto
import br.com.company.stock.service.AcaoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/stock")
class StockController @Autowired constructor(val service: AcaoService){
    @GetMapping("/data/{ticker}")
    fun getAnalise(@PathVariable ticker: String): Mono<StockAnalysisDto> {
        return service.getAnalise(ticker)
    }
}