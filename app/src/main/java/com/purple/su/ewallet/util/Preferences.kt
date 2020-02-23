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

    fun saveSGDBalance(time: Int) {
        sharedPreferences?.edit(commit = true) {
            putInt(PREF_SGD_BALANCE, time)
        }
    }

    fun getSGDBalance(): Int {
        val defVal = 1000
        val time = sharedPreferences?.getInt(PREF_SGD_BALANCE, defVal)
        time?.let { return it }
        return defVal
    }

    fun saveUSDBalance(time: Int) {
        sharedPreferences?.edit(commit = true) {
            putInt(PREF_USD_BALANCE, time)
        }
    }

    fun getUSDBalance(): Int {
        val defVal = 500
        val time = sharedPreferences?.getInt(PREF_USD_BALANCE, defVal)
        time?.let { return it }
        return defVal
    }

    fun saveEURBalance(time: Int) {
        sharedPreferences?.edit(commit = true) {
            putInt(PREF_EUR_BALANCE, time)
        }
    }

    fun getEURBalance(): Int {
        val defVal = 300
        val time = sharedPreferences?.getInt(PREF_EUR_BALANCE, defVal)
        time?.let { return it }
        return defVal
    }
}