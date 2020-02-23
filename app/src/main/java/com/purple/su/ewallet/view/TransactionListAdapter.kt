package com.purple.su.ewallet.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.purple.su.ewallet.R
import com.purple.su.ewallet.data.Transaction
import com.purple.su.ewallet.databinding.TransactionItemBinding

/**
 * Created by Su Thwe on 2020-02-23.
 */
class TransactionListAdapter(private val transactions: ArrayList<Transaction>) : RecyclerView.Adapter<TransactionListAdapter.TransactionViewHolder>() {

    fun updateList(transList: List<Transaction>) {
        transactions.addAll(transList)
        notifyDataSetChanged()
    }

    class TransactionViewHolder(val view: TransactionItemBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<TransactionItemBinding>(inflater, R.layout.transaction_item, parent, false)
        return TransactionViewHolder(view)
    }

    override fun getItemCount() = transactions.size

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.view.trans = transactions[position]
    }
}