package com.purple.su.ewallet.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.purple.su.ewallet.data.Transaction

/**
 * Created by Su Thwe on 2020-02-22.
 */
@Dao
interface TransactionDao {

    @Insert
    suspend fun insert(trans: Transaction)

    @Query("SELECT * FROM `transaction`")
    suspend fun getAllTransactions(): List<Transaction>
}