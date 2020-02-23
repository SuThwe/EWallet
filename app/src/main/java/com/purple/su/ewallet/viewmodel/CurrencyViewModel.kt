package com.purple.su.ewallet.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonElement
import com.purple.su.ewallet.api.CurrencyApiService
import com.purple.su.ewallet.data.Rate
import com.purple.su.ewallet.data.Transaction
import com.purple.su.ewallet.db.TransactionDatabase
import com.purple.su.ewallet.util.Preferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

/**
 * Created by Su Thwe on 2020-02-21.
 */
class CurrencyViewModel(application: Application): BaseViewModel(application) {

    private val apiService = CurrencyApiService()
    private val disposable = CompositeDisposable()

    val rate = MutableLiveData<Rate>()
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<Boolean>()

    fun getTodayRates(base: String) {
        loading.value = true
        disposable.add(
            apiService.getTodayRate(base)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<JsonElement>() {
                    override fun onSuccess(jsonElement: JsonElement) {
                        val rates = convertJsonElementToRateObj(jsonElement)

                        val pref = Preferences(getApplication())
                        pref.saveSGDRate(rates.sgd.toFloat())
                        pref.saveUSDRate(rates.usd.toFloat())
                        pref.saveEURRate(rates.eur.toFloat())

                        rate.value = rates
                        loading.value = false
                        error.value = false
                    }

                    override fun onError(e: Throwable) {
                        loading.value = false
                        error.value = true
                        e.printStackTrace()
                    }
                })
        )
    }

    private fun convertJsonElementToRateObj(jsonElement: JsonElement): Rate {
        var rate = Rate(1.0, 1.0, 1.0)
        val json = jsonElement.asJsonObject
        json?.let {
            val objRates = json["rates"].asJsonObject
            rate.sgd = String.format("%.2f", objRates["SGD"].asDouble).toDouble()
            rate.usd = String.format("%.2f", objRates["USD"].asDouble).toDouble()

            objRates["EUR"]?.let {
                rate.eur = String.format("%.2f", objRates["EUR"].asDouble).toDouble()
            }
        }
        return rate
    }

    fun storeTransaction(transaction: Transaction) {
        launch {
            val dao = TransactionDatabase(getApplication()).transactionDao()
            dao.insert(transaction)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
