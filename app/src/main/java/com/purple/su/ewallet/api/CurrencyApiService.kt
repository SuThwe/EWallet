package com.purple.su.ewallet.api

import com.google.gson.JsonElement
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Su Thwe on 2020-02-21.
 */
class CurrencyApiService {
    private val BASE_URL = "https://api.exchangeratesapi.io"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(CurrencyApi::class.java)

    /* base="EUR" , symbols="SGD,USD,EUR", then got error,
       so change the symbols for "EUR"
    */
    fun getTodayRate(base: String): Single<JsonElement> {
        var symbols = when(base) {
            "EUR" -> "SGD,USD"
            else -> "SGD,USD,EUR"
        }
        return api.getTodayRate(base, symbols)
    }
}