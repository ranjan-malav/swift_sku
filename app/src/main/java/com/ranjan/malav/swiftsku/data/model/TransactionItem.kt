package com.ranjan.malav.swiftsku.data.model

import androidx.room.Embedded


data class TransactionItem(
    @Embedded val pluItem: PriceBookItem,
    var quantity: Int,
)
