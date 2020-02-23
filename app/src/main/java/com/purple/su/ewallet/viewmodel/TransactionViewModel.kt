package com.purple.su.ewallet.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.purple.su.ewallet.data.Transaction
import com.purple.su.ewallet.db.TransactionDatabase
import kotlinx.coroutines.launch

/**
 * Created by Su Thwe on 2020-02-23.
 */
class TransactionViewModel(application: Application) : BaseViewModel(application) {
    val transactions = MutableLiveData<List<Transaction>>()
    val noTransactionMsg = MutableLiveData<Boolean>()

    fun retrieveHistories() {
        launch {
            val transactionList = TransactionDatabase(getApplication()).transactionDao().getAllTransactions()
            transactions.value = transactionList

            noTransactionMsg.value = transactionList.isNullOrEmpty()
        }
    }
}