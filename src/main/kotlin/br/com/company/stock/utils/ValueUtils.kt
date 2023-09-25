package br.com.company.stock.utils

import io.netty.util.internal.StringUtil

class ValueUtils {
    companion object {
        fun getDoubleValue(value: String): Double =
            if (StringUtil.isNullOrEmpty(value) || value == "-") 0.00 else requireNotNull(value).trim()
                .replace(",", ".")
                .replace("%", "")
                .toDouble()
    }

}