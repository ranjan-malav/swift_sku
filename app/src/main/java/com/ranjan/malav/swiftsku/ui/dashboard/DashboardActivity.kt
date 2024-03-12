package com.ranjan.malav.swiftsku.ui.dashboard

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ranjan.malav.swiftsku.data.model.PriceBookItem
import com.ranjan.malav.swiftsku.databinding.ActivityDashboardBinding
import com.ranjan.malav.swiftsku.ui.utils.UiUtils.formatAmount
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DashboardActivity : AppCompatActivity(), BookItemsAdapter.Callback {

    private lateinit var binding: ActivityDashboardBinding

    private val viewModel: DashboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBinding.inflate(layoutInflater)
//        setContentView(binding.root)

        setContent { DashboardLayout(viewModel) }

        viewModel.getPriceBookData()
        viewModel.getTransactions()

        viewModel.bookItems.observe(this) {
            binding.rvBookItems.apply {
                layoutManager = GridLayoutManager(
                    this@DashboardActivity, 5
                )
                adapter = BookItemsAdapter(it, this@DashboardActivity)
            }
        }

//        viewModel.selectedItemsLive.observe(this) {
//            binding.rvCartItems.apply {
//                layoutManager = LinearLayoutManager(this@DashboardActivity)
//                adapter = CartItemsAdapter(it)
//            }
//        }

//        viewModel.totals.observe(this) {
//            binding.tvSubTotalValue.text = formatAmount(it.subTotalAmount)
//            binding.tvTaxValue.text = formatAmount(it.taxAmount)
//            binding.tvDiscountValue.text = formatAmount(it.discountAmount)
//            binding.tvGrandTotalValue.text = formatAmount(it.grandTotal)
//        }

        viewModel.transactions.observe(this) {
            binding.tvCustomerCountValue.text = "${it.size}"
            binding.tvTotalSalesValue.text =
                formatAmount(it.sumOf { it.txnTotalGrandAmount.toDouble() })

            binding.rvBookItems.apply {
                layoutManager = LinearLayoutManager(this@DashboardActivity)
                adapter = TransactionAdapter(it)
            }
        }

        binding.btnSave.setOnClickListener {
            viewModel.saveTransaction()
        }
        binding.btnRecall.setOnClickListener {
            viewModel.recallTransaction()
        }
        binding.btnHistory.setOnClickListener {

        }
        binding.btnComplete.setOnClickListener {
            viewModel.completeTransaction()
        }
    }

    override fun selectItem(item: PriceBookItem) {
        viewModel.selectItem(item)
    }
}