package com.purple.su.ewallet.api

import com.google.gson.JsonElement
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by Su Thwe on 2020-02-21.
 */
interface CurrencyApi {

    @GET("latest?base=SGD&symbols=SGD,USD,EUR")
    fun getTodayRate(): Single<JsonElement>
}