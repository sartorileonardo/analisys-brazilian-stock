package br.com.company.stock.controller

import br.com.company.stock.dto.StockAnalysisDto
import br.com.company.stock.service.AcaoService
import io.swagger.annotations.ApiOperation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@CrossOrigin(origins = ["http://localhost:3000"])
@RestController
@RequestMapping("/stock")
class StockController @Autowired constructor(val service: AcaoService){
    @ApiOperation(value = "Display with stock analysis", response = StockAnalysisDto::class)
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Sucessful operation"),
            ApiResponse(responseCode = "400", description = "Invalid input with ticker field"),
            ApiResponse(responseCode = "404", description = "Ticker not found"),
            ApiResponse(responseCode = "500", description = "Internal server error"),
        ]
    )
    @GetMapping("/data/{ticker}")
    fun getAnalise(@PathVariable ticker: String): Mono<StockAnalysisDto> {
        return service.getAnalise(ticker)
    }
}