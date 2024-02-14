package br.com.company.stock.validation

import br.com.company.stock.exception.BusinessException

class TickerValidation {
    companion object {
        fun validateTicker(ticker: String): Boolean {
            return validateIfEmpty(ticker.trim()).and(validateIfContainsOnlyNumbers(ticker))
                .and(validateIfContainsOnlyLetters(ticker)).and(validateIfFinishedWithAcceptedNumber(ticker))
                .and(validateIfIsBDR(ticker))
        }

        private fun validateIfIsBDR(ticker: String) = if (listOf(
                "32", "33", "34", "35"
            ).contains(ticker.substring(ticker.length - 2))
        ) throw BusinessException("O sistema não suporta ticker de BDR's, tente novamente com um ticker de empresa brasileira!") else true

        private fun validateIfFinishedWithAcceptedNumber(ticker: String) = if (!listOf("3", "4").contains(
                ticker.last().toString()
            ) && ticker.substring(ticker.length - 2) != "11"
        ) throw BusinessException("Ticker deve terminar com 3, 4 ou 11, exemplo: ABEV3, BBDC4, TAEE11!") else true

        private fun validateIfContainsOnlyLetters(ticker: String) =
            if (ticker.matches(Regex("^[a-zA-Z]+\$"))) throw BusinessException("Ticker não pode ser somente letras!") else true

        private fun validateIfContainsOnlyNumbers(ticker: String) =
            if (ticker.matches(Regex("\\d+"))) throw BusinessException("Ticker não pode ser somente numeros!") else true

        private fun validateIfEmpty(ticker: String) =
            if (ticker.isBlank()) throw BusinessException("Ticker é obrigatório!") else true
    }

}