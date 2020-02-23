package com.purple.su.ewallet.viewmodel

import android.content.Context
import com.purple.su.ewallet.util.Preferences
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


/**
 * Created by Su Thwe on 2020-02-22.
 */
class CurrencyConverter(private val context: Context) {

    fun currencyConvert(from: String, to: String, amount: Double) : Double  {
        return 100.0
    }

    fun checkBalance(from: String, amount: Double) : Boolean {
        val pref = Preferences(context)
        val balance =  when(from) {
            "SGD" -> pref.getSGDBalance()
            "USD" -> pref.getUSDBalance()
            else -> pref.getEURBalance()
        }

        if(amount < balance)
            return true
        return false
    }

    fun getTodayDate(): String {
        val DATE_TIME_FORMATTER: DateTimeFormatter =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
                .withZone(ZoneId.systemDefault())

        return DATE_TIME_FORMATTER.format(Date().toInstant())
    }
}