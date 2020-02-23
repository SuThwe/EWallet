package com.purple.su.ewallet.viewmodel

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.purple.su.ewallet.util.Preferences
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.junit.Assert.*
import org.junit.Before

/**
 * Created by Su Thwe on 2020-02-23.
 */
class CurrencyConverterTest : Spek({

    val mockPref = mock<Preferences>()
    val instance = mock<CurrencyConverter>()

    @Before
    fun setup() {

    }

    describe("Test 1") {
        val res = instance.currencyConvert(any(), any(), any())
        assertEquals(100, res)
    }
})