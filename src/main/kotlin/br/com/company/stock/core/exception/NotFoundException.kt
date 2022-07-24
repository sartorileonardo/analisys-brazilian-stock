package br.com.company.stock.core.exception

class NotFoundException(message: String = "Stock not found with this id!") : RuntimeException(message)