package com.ranjan.malav.swiftsku.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ranjan.malav.swiftsku.data.model.TransactionItem
import com.ranjan.malav.swiftsku.databinding.ListCartItemBinding
import com.ranjan.malav.swiftsku.ui.utils.UiUtils


class CartItemsAdapter(
    val data: List<TransactionItem>
) : RecyclerView.Adapter<CartItemsAdapter.BookHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        return BookHolder(
            ListCartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount() = data.size

    inner class BookHolder(private val binding: ListCartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val item = data[position]

            with(binding) {
                tvItemName.text = item.pluItem.itemName
                tvItemQty.text = "x${item.quantity}"
                tvItemTotal.text = UiUtils.formatAmount((item.quantity * item.pluItem.price))
            }
        }
    }
}