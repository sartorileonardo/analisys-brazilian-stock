package br.com.company.stock.core.controller.dto

data class ErrorDTO(
    val status: Int,
    val error: String,
    val message: String?,
    val path: String
)