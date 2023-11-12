package br.com.company.stock.utils

import java.util.*

class ValueUtils {
    companion object {
        fun getDoubleValue(anyValue: Any): Double {
            val minValue = "0.00"
            val value = Objects.requireNonNullElse(anyValue, minValue) as String
            val regexValidDouble = """^[+\-]?\d+[.,]?\d*$""".toRegex()

            if (value.isEmpty() || !value.matches(regexValidDouble)) {
                throw IllegalArgumentException("Invalid double value: $value")
            }

            return value.trim().replace(",", ".").replace("%", "").toDouble()
        }

    }

}