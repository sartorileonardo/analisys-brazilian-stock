package br.com.company.stock.utils

import java.util.*

class ValueUtils {
    companion object {
        fun getDoubleValue(anyValue: Any): Double {
            val minValue = "0.00"
            val value = Objects.requireNonNullElse(anyValue, minValue) as String

            return value
                .trim()
                .replace(",", ".")
                .replace("%", "")
                .replace("null", minValue)
                .toDouble()
        }

    }

}