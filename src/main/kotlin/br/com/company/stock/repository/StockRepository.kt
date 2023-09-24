package br.com.company.stock.repository

import br.com.company.stock.entity.StockEntity
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface StockRepository : ReactiveMongoRepository<StockEntity, String>