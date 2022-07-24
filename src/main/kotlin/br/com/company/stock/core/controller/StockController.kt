package br.com.company.stock.core.controller

import br.com.company.stock.core.service.StockService
import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"])
@RestController
@RequestMapping("/stock")
@Api(description = "Analise de empresa brasileira de capital aberto")
class StockController @Autowired constructor(val service: StockService) {
    @GetMapping("/analisys/{ticker}")
    fun getAnalisys(@PathVariable ticker: String) = service.getAnalisys(ticker.toUpperCase())
}