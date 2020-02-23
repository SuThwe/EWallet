package com.purple.su.ewallet.api

import com.google.gson.JsonElement
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Su Thwe on 2020-02-21.
 */
interface CurrencyApi {

    @GET("latest")
    fun getTodayRate(@Query("base") base: String, @Query("symbols") symbols: String): Single<JsonElement>
}