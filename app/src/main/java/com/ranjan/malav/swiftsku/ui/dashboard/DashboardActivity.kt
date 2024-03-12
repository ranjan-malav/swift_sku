package com.ranjan.malav.swiftsku.ui.dashboard

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ranjan.malav.swiftsku.data.model.PriceBookItem
import com.ranjan.malav.swiftsku.databinding.ActivityDashboardBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DashboardActivity : AppCompatActivity(), BookItemsAdapter.Callback {

    private lateinit var binding: ActivityDashboardBinding

    private val viewModel: DashboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getPriceBookData()

        viewModel.bookItems.observe(this) {
            binding.rvBookItems.apply {
                layoutManager = GridLayoutManager(
                    this@DashboardActivity, 5
                )
                adapter = BookItemsAdapter(it, this@DashboardActivity)
            }
        }

        viewModel.selectedItemsLive.observe(this) {
            binding.rvCartItems.apply {
                layoutManager = LinearLayoutManager(this@DashboardActivity)
                adapter = CartItemsAdapter(it)
            }
        }

        viewModel.totals.observe(this) {
            binding.tvSubTotalValue.text = "\$${String.format("%.2f", it.subTotalAmount)}"
            binding.tvTaxValue.text = "\$${String.format("%.2f", it.taxAmount)}"
            binding.tvDiscountValue.text = "\$${String.format("%.2f", it.discountAmount)}"
            binding.tvGrandTotalValue.text = "\$${String.format("%.2f", it.grandTotal)}"
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