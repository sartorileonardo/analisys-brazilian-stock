package br.com.company.stock.service

import br.com.company.stock.business.StockBusiness
import br.com.company.stock.config.StockParametersApiConfig
import br.com.company.stock.dto.StockAnalysisDto
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@CacheConfig(cacheNames = ["analise"])
@Service
class AcaoService(val acaoConfig: StockParametersApiConfig){
    @Cacheable(value = ["analise"])
    fun getAnalise(ticker: String): Mono<StockAnalysisDto> = StockBusiness(acaoConfig).getAnalise(ticker)
}
