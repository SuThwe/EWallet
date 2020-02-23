package com.purple.su.ewallet.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.purple.su.ewallet.data.Transaction

/**
 * Created by Su Thwe on 2020-02-22.
 */

@Database(entities = [Transaction::class], version = 1)
abstract class TransactionDatabase: RoomDatabase() {
    abstract fun transactionDao(): TransactionDao

    companion object {
        @Volatile private var instance: TransactionDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            TransactionDatabase::class.java,
            "ewalletdb"
        ).build()
    }
}