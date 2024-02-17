package br.com.company.stock.config

import br.com.company.stock.service.StockService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled


@Configuration
@EnableCaching
@EnableScheduling
class CacheConfig(
    @Autowired
    val cacheManager: CacheManager
) {
    companion object {
        var LOGGER: Logger? = LoggerFactory.getLogger(CacheConfig::class.java)
    }
    @Scheduled(cron = "0 0 0 */3 * *") // Runs at midnight on the first day of every 3rd month
    fun clearCache() {
        cacheManager.getCache("analisys")?.clear()
        cacheManager.getCache("fundamentals")?.clear()
        LOGGER?.info("Memory cache was cleaned!")
    }
}