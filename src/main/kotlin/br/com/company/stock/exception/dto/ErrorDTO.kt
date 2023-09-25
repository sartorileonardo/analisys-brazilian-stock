package br.com.company.stock.exception.dto

data class ErrorDTO(
    val status: Int,
    val error: String,
    val message: String?,
    val path: String
)