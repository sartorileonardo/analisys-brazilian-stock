package br.com.company.stock.config

import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Configuration


@Configuration
@EnableCaching
class EmbeddedCacheConfig {

    //TODO: Realizar a implementação correta para configurar o tempo de validade do cache
    /*
    @Bean
    fun config(): Config {
        val config = Config()
        val mapConfig = MapConfig()
        mapConfig.setTimeToLiveSeconds(10)
        config.getMapConfigs().put("analise", mapConfig);
        return Config()
    }

     */


}