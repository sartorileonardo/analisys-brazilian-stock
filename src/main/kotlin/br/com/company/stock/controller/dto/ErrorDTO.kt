package br.com.company.stock.controller.dto

data class ErrorDTO(
    val status: Int,
    val error: String,
    val message: String?,
    val path: String
)