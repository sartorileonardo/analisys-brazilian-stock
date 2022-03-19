package br.com.company.stock.controller.dto

data class ErrorDto(
    val status: Int,
    val error: String,
    val message: String?,
    val path: String
)