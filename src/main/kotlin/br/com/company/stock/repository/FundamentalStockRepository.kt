package br.com.company.stock.repository

import br.com.company.stock.entity.FundamentalStockEntity
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface FundamentalStockRepository : ReactiveMongoRepository<FundamentalStockEntity, String>