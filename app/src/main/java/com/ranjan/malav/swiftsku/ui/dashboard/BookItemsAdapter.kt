package com.ranjan.malav.swiftsku.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ranjan.malav.swiftsku.data.model.PriceBookItem
import com.ranjan.malav.swiftsku.databinding.ListItemBinding

class BookItemsAdapter(
    val data: List<PriceBookItem>,
    val callback: Callback
) : RecyclerView.Adapter<BookItemsAdapter.BookHolder>() {

    interface Callback {
        fun selectItem(item: PriceBookItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        return BookHolder(
            ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount() = data.size

    inner class BookHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val item = data[position]

            with(binding) {
                tvItemName.text = item.itemName
                root.setOnClickListener {
                    callback.selectItem(item)
                }
            }
        }
    }
}