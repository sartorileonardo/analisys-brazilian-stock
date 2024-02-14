package br.com.company.stock.utils

import java.time.LocalDate

class DateUtils {
    companion object {
        fun getTodayDate(): LocalDate = LocalDate.now()
        fun isFundamentalExpired(today: LocalDate, createDate: LocalDate): Boolean {
            val expirationDate = createDate.plusMonths(4)
            return today.isAfter(expirationDate)
        }
    }
}