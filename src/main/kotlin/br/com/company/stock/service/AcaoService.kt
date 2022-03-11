package br.com.company.stock.service

import br.com.company.stock.business.AcaoBusiness
import br.com.company.stock.config.AcaoConfig
import br.com.company.stock.dto.AnaliseAcaoDto
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@CacheConfig(cacheNames = ["analise"])
@Service
class AcaoService(val acaoConfig: AcaoConfig){
    @Cacheable(value = ["analise"])
    fun getAnalise(ticker: String): Mono<AnaliseAcaoDto> = AcaoBusiness(acaoConfig).getAnalise(ticker)
}
