package com.purple.su.ewallet.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonElement
import com.purple.su.ewallet.api.CurrencyApiService
import com.purple.su.ewallet.data.Rate
import com.purple.su.ewallet.data.Transaction
import com.purple.su.ewallet.db.TransactionDatabase
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

    fun getTodayRates() {
        loading.value = true
        disposable.add(
            apiService.getTodayRate()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<JsonElement>() {
                    override fun onSuccess(jsonElement: JsonElement) {
                        var rates = convertJsonElementToList(jsonElement)
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

    private fun convertJsonElementToList(jsonElement: JsonElement): Rate {
        var rate = Rate("", "", "")
        val json = jsonElement.asJsonObject
        json?.let {
            val objRates = json["rates"].asJsonObject
            rate.sgd = objRates["SGD"].asString
            rate.usd = objRates["USD"].asString
            rate.eur = objRates["EUR"].asString
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
