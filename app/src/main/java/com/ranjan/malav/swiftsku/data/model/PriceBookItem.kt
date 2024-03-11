package com.ranjan.malav.swiftsku.data.model


data class PriceBookItem(
    val pluId: String,
    val itemName: String,
    val taxRates: List<Float> = arrayListOf(), // 5.5%, 6.0%
    val price: Float,
)
