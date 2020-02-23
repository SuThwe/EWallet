package com.purple.su.ewallet.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Su Thwe on 2020-02-22.
 */
@Entity
data class Transaction (
    @PrimaryKey(autoGenerate = true)
    var transactionId: Int = 0,

    @ColumnInfo(name = "convertFrom")
    var convertFrom: String,

    @ColumnInfo(name = "convertTo")
    var convertTo: String,

    @ColumnInfo(name = "initialAmount")
    var initialAmount: String,

    @ColumnInfo(name = "convertedAmount")
    var convertedAmount: String,

    @ColumnInfo(name = "transactionDate")
    var transactionDate: String
)