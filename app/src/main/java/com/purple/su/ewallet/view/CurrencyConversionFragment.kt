package com.purple.su.ewallet.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation

import com.purple.su.ewallet.R
import com.purple.su.ewallet.data.Transaction
import com.purple.su.ewallet.databinding.FragmentCurrencyConversionBinding
import com.purple.su.ewallet.util.afterTextChanged
import com.purple.su.ewallet.viewmodel.CurrencyConverter
import com.purple.su.ewallet.viewmodel.CurrencyViewModel
import kotlinx.android.synthetic.main.fragment_currency_conversion.*
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Created by Su Thwe on 2020-02-20.
 */
class CurrencyConversionFragment : Fragment() {

    private lateinit var viewModel: CurrencyViewModel
    private lateinit var dataBinding: FragmentCurrencyConversionBinding

    private var fromCurrency = ""
    private var toCurrency = ""
    private lateinit var currencyConverter: CurrencyConverter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(CurrencyViewModel::class.java)
        viewModel.getTodayRates()

        currencyConverter = CurrencyConverter(context!!)

        edit_from_amount.afterTextChanged {
            convertAmount()
        }

        btn_convert.setOnClickListener{
            btnConvertClicked()
        }

        setSpinner()
        observerViewModel()
    }

    private fun setSpinner() {

        ArrayAdapter.createFromResource(
            context!!,
            R.array.currencies,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner_from.adapter = adapter
            spinner_to.adapter = adapter
        }

        spinner_from.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                fromCurrency = spinner_from.selectedItem.toString()
                convertAmount()
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
            }
        }

        spinner_to.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                toCurrency = spinner_from.selectedItem.toString()
                convertAmount()
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
            }
        }

    }

    private fun observerViewModel() {
        viewModel.rate.observe(this, Observer { rate ->
            rate?.let {
                dataBinding.rates = rate
                currency_layout.visibility = View.VISIBLE
            }
        })

        viewModel.loading.observe(this, Observer { isError ->
            isError?.let {
                loading.visibility = when(it) {
                    true -> View.VISIBLE
                    false -> View.GONE
                }
                if(it) {
                    currency_layout.visibility = View.GONE
                    errorMsg.visibility = View.GONE
                }
            }
        })

        viewModel.error.observe(this, Observer { isError ->
            isError.let {
                errorMsg.visibility = when(it) {
                    true -> View.VISIBLE
                    false -> View.GONE
                }
            }
        })
    }

    private fun convertAmount() {
        if(!edit_from_amount.text.isNullOrEmpty()) {
            val convertedAmount = currencyConverter.currencyConvert(fromCurrency, toCurrency, edit_from_amount.text.toString().toDouble())
            edit_to_amount.setText(convertedAmount.toString())
        }
    }

    private fun btnConvertClicked() {
        if(edit_from_amount.text.isNullOrEmpty() || edit_from_amount.text.isNullOrEmpty()) {
            Toast.makeText(context, "Please fill in the amount to transfer", Toast.LENGTH_SHORT).show()
        }
        else {
            if (currencyConverter.checkBalance(fromCurrency, edit_from_amount.text.toString().toDouble())) {
                val transaction = Transaction(
                    convertFrom = fromCurrency,
                    convertTo = toCurrency,
                    initialAmount = edit_from_amount.text.toString(),
                    convertedAmount = edit_to_amount.text.toString(),
                    transactionDate = currencyConverter.getTodayDate()
                )
                viewModel.storeTransaction(transaction)
                edit_from_amount.setText("")
                edit_to_amount.setText("")
                Toast.makeText(context, "Transferred success", Toast.LENGTH_SHORT).show()
            }
            else {
                val builder = AlertDialog.Builder(context!!)
                builder.setTitle("EWallet")
                builder.setMessage("Insufficient wallet balance")
                builder.setPositiveButton("OK"){dialog, which ->
                    dialog.dismiss()
                }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_currency_conversion, container, false)
        return dataBinding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_trans_history -> view?.let {
                Navigation.findNavController(it).navigate(CurrencyConversionFragmentDirections.actionCurrencyConversionFragmentToTransactionHistoryFragment())
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
