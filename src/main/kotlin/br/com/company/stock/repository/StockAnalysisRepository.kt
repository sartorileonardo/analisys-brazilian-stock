package br.com.company.stock.repository

import br.com.company.stock.repository.entity.StockAnalysisEntity
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface StockAnalysisRepository : ReactiveMongoRepository<StockAnalysisEntity, String>