package com.purple.su.ewallet.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.purple.su.ewallet.R
import com.purple.su.ewallet.databinding.FragmentCurrencyConversionBinding
import com.purple.su.ewallet.viewmodel.CurrencyViewModel
import kotlinx.android.synthetic.main.fragment_currency_conversion.*

/**
 * Created by Su Thwe on 2020-02-20.
 */
class CurrencyConversionFragment : Fragment() {

    private lateinit var viewModel: CurrencyViewModel
    private lateinit var dataBinding: FragmentCurrencyConversionBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currency_layout.visibility = View.GONE

        viewModel = ViewModelProviders.of(this).get(CurrencyViewModel::class.java)
        viewModel.getTodayRates()

        observerViewModel()
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_currency_conversion, container, false)
        return dataBinding.root
    }
}
