package br.com.company.stock.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled


@Configuration
@EnableCaching
@EnableScheduling
class EmbeddedCacheConfig(
    @Autowired
    val cacheManager: CacheManager
) {
    @Scheduled(fixedRate = 300000000)
    fun clearCache(){
        cacheManager.getCache("analise")?.clear()
    }

}