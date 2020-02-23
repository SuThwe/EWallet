package com.purple.su.ewallet.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.purple.su.ewallet.R
import com.purple.su.ewallet.viewmodel.TransactionViewModel
import kotlinx.android.synthetic.main.fragment_transaction_history.*

/**
 * Created by Su Thwe on 2020-02-20.
 */
class TransactionHistoryFragment : Fragment() {

    private lateinit var viewModel: TransactionViewModel
    private var tranListAdapter = TransactionListAdapter(arrayListOf())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(TransactionViewModel::class.java)
        viewModel.retrieveHistories()

        transactionList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = tranListAdapter
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.transactions.observe(this, Observer { list ->
            list?.let {
                tranListAdapter.updateList(it)
            }
        })

        viewModel.noTransactionMsg.observe(this, Observer { isEmpty ->
            no_transaction.visibility = when(isEmpty) {
                true -> View.VISIBLE
                false -> View.GONE
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaction_history, container, false)
    }


}
