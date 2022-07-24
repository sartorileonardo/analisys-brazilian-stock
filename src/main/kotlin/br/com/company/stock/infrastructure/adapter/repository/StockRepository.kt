package br.com.company.stock.infrastructure.adapter.repository

import br.com.company.stock.infrastructure.adapter.repository.entity.StockEntity
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface StockRepository : ReactiveMongoRepository<StockEntity, String>