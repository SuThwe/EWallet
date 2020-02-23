package com.purple.su.ewallet.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

/**
 * Created by Su Thwe on 2020-02-22.
 */
class Preferences {
    companion object {
        private const val PREF_SGD_BALANCE = "SGD balance"
        private const val PREF_USD_BALANCE = "USD balance"
        private const val PREF_EUR_BALANCE = "EUR balance"
        private const val PREF_SGD_RATE = "SGD rate"
        private const val PREF_USD_RATE = "USD rate"
        private const val PREF_EUR_RATE = "EUR rate"
        private var sharedPreferences: SharedPreferences? = null

        @Volatile private var instance: Preferences? = null
        private val LOCK = Any()

        operator fun invoke(context: Context): Preferences = instance ?: synchronized(LOCK) {
            instance ?: buildHelper(context).also {
                instance = it
            }
        }

        private fun buildHelper(context: Context): Preferences {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return Preferences()
        }
    }

    fun getBalance(currency: String): Float {
        return when(currency) {
            "SGD" -> getSGDBalance()
            "USD" -> getUSDBalance()
            else -> getEURBalance()
        }
    }

    fun saveBalance(currency: String, amount: Float){
        when(currency) {
            "SGD" -> saveSGDBalance(amount)
            "USD" -> saveUSDBalance(amount)
            else  -> saveEURBalance(amount)
        }
    }

    fun saveSGDBalance(amount: Float) {
        sharedPreferences?.edit(commit = true) {
            putFloat(PREF_SGD_BALANCE, amount)
        }
    }

    fun getSGDBalance(): Float {
        val defVal = 1000f
        val amount = sharedPreferences?.getFloat(PREF_SGD_BALANCE, defVal)
        amount?.let { return it }
        return defVal
    }

    fun saveUSDBalance(amount: Float) {
        sharedPreferences?.edit(commit = true) {
            putFloat(PREF_USD_BALANCE, amount)
        }
    }

    fun getUSDBalance(): Float {
        val defVal = 500f
        val amount = sharedPreferences?.getFloat(PREF_USD_BALANCE, defVal)
        amount?.let { return it }
        return defVal
    }

    fun saveEURBalance(amount: Float) {
        sharedPreferences?.edit(commit = true) {
            putFloat(PREF_EUR_BALANCE, amount)
        }
    }

    fun getEURBalance(): Float {
        val defVal = 300f
        val amount = sharedPreferences?.getFloat(PREF_EUR_BALANCE, defVal)
        amount?.let { return it }
        return defVal
    }

    fun getRate(currency: String): Float {
        return when(currency) {
            "SGD" -> getSGDRate()
            "USD" -> getUSDRate()
            else -> getEURRate()
        }
    }

    fun saveSGDRate(amount: Float) {
        sharedPreferences?.edit(commit = true) {
            putFloat(PREF_SGD_RATE, amount)
        }
    }

    fun getSGDRate(): Float {
        val defVal = 1.0f
        val amount = sharedPreferences?.getFloat(PREF_SGD_RATE, defVal)
        amount?.let { return it }
        return defVal
    }

    fun saveUSDRate(amount: Float) {
        sharedPreferences?.edit(commit = true) {
            putFloat(PREF_USD_RATE, amount)
        }
    }

    fun getUSDRate(): Float {
        val defVal = 1.0f
        val amount = sharedPreferences?.getFloat(PREF_USD_RATE, defVal)
        amount?.let { return it }
        return defVal
    }

    fun saveEURRate(amount: Float) {
        sharedPreferences?.edit(commit = true) {
            putFloat(PREF_EUR_RATE, amount)
        }
    }

    fun getEURRate(): Float {
        val defVal = 1.0f
        val amount = sharedPreferences?.getFloat(PREF_EUR_RATE, defVal)
        amount?.let { return it }
        return defVal
    }
}