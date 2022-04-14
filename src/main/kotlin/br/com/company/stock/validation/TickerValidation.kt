package br.com.company.stock.validation

import br.com.company.stock.exception.BusinessException
import org.springframework.util.ObjectUtils
import org.springframework.util.StringUtils

class TickerValidation {
    fun validarTicker(ticker: String): Boolean {
        return validarSeForVazio(ticker)
            .and(validarSePossuiSomenteNumeros(ticker))
            .and(validarSePossuiSomenteNumeros(ticker))
            .and(validarSePossuiSomenteLetras(ticker))
            .and(validarSeTerminaComDigitoAceito(ticker))
            .and(validarSeForBDR(ticker))
    }

    private fun validarSeForBDR(ticker: String) = if(listOf("32", "33", "34", "35").contains(ticker.substring(ticker.length - 2))) throw BusinessException("O sistema não suporta ticker de BDR's, tente novamente com um ticker de empresa brasileira!") else true

    private fun validarSeTerminaComDigitoAceito(ticker: String) = if(!listOf("3", "4").contains(ticker.last().toString()) && ticker.substring(ticker.length - 2) != "11")
            throw BusinessException("Ticker deve terminar com 3, 4 ou 11, exemplo: ABEV3, BBDC4, TAEE11!") else true

    private fun validarSePossuiSomenteLetras(ticker: String) = if(ticker.matches(Regex("^[a-zA-Z]+\$"))) throw BusinessException("Ticker não pode ser somente letras!") else true

    private fun validarSePossuiSomenteNumeros(ticker: String) = if(ticker.matches(Regex("\\d+"))) throw BusinessException("Ticker não pode ser somente numeros!") else true

    private fun validarSeForVazio(ticker: String) = if(ticker.isNullOrBlank()) throw BusinessException("Ticker é obrigatório!") else true
}