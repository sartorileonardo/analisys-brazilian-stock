package br.com.company.stock.config

import br.com.company.stock.repository.StockAnalysisRepository
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories
import java.time.Duration
import java.util.concurrent.TimeUnit

@Configuration
@EnableReactiveMongoRepositories(basePackageClasses = arrayOf(StockAnalysisRepository::class))
class DatabaseConfig(
    private val config: StockParametersApiConfig
) : AbstractReactiveMongoConfiguration() {

    private val databaseName = config.databaseName
    private val databaseUri = config.urlDatabase

    override fun getDatabaseName() = databaseName

    override fun reactiveMongoClient(): MongoClient = mongoClient()

    @Bean
    fun mongoClient(): MongoClient {
        val connectionString =
            ConnectionString(databaseUri)
        val settings: MongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .applyToSocketSettings{
                it.connectTimeout(config.timeoutDatabase.toInt(), TimeUnit.SECONDS)
            }
            .applyToConnectionPoolSettings {
                it.maxWaitTime(config.timeoutDatabase.toLong(), TimeUnit.SECONDS)
            }
            .applyToClusterSettings {
                it.serverSelectionTimeout(config.timeoutDatabase.toLong(), TimeUnit.SECONDS)
            }
            .retryWrites(true)
            .retryReads(true)
            .build()
        val mongoClient = MongoClients.create(settings)
        val database = mongoClient.getDatabase(databaseName)
        return mongoClient
    }

    @Bean
    fun reactiveMongoTemplate()
            = ReactiveMongoTemplate(mongoClient(), databaseName)
}