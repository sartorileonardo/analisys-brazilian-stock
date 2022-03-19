package br.com.company.stock.validation

import br.com.company.stock.exception.BusinessException
import org.springframework.util.ObjectUtils

class TickerValidation {
    fun validarTicker(ticker: String) {
        validarSeForVazio(ticker)
        validarSePossuiSomenteNumeros(ticker)
        validarSePossuiSomenteLetras(ticker)
        validarSeTerminaComDigitoAceito(ticker)
        validarSeForBDR(ticker)
    }

    private fun validarSeForBDR(ticker: String) {
        if (listOf("32", "33", "34", "35").contains(ticker.trim().substring(ticker.trim().length - 2))) {
            throw BusinessException("O sistema não suporta ticker de BDR's, tente novamente com um ticker de empresa brasileira!")
        }
    }

    private fun validarSeTerminaComDigitoAceito(ticker: String) {
        if (!listOf("3", "4", "11").contains(ticker.trim().last().toString())) {
            throw BusinessException("Ticker deve terminar com 3, 4 ou 11, exemplo: ABEV3, BBDC4, TAEE11!")
        }
    }

    private fun validarSePossuiSomenteLetras(ticker: String) {
        if (ticker.trim().matches(Regex("^[a-zA-Z]+\$"))) {
            throw BusinessException("Ticker não pode ser somente letras!")
        }
    }

    private fun validarSePossuiSomenteNumeros(ticker: String) {
        if (ticker.trim().matches(Regex("\\d+"))) {
            throw BusinessException("Ticker não pode ser somente numeros!")
        }
    }

    private fun validarSeForVazio(ticker: String) {
        if (ObjectUtils.isEmpty(ticker)) {
            throw BusinessException("Ticker é obrigatório!")
        }
    }
}