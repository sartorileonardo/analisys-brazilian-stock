package br.com.company.stock.utils

import java.util.*

class ValueUtils {
    companion object {
        fun getDoubleValue(anyValue: Any): Double {
            val value = Objects.requireNonNullElse(anyValue, "0.00") as String
            if (value == "-") {
                return value.toDouble();
            }
            return value.trim()
                .replace(",", ".")
                .replace("%", "")
                .toDouble()
        }

    }

}