package com.ranjan.malav.swiftsku.ui.dashboard

object DiscountEngine {

    data class DiscountInfo(
        val discountPerc: Float,
        val discountAmount: Float
    )

    val discountStoreA = DiscountInfo(0.1f, 25f)
    val discountStoreB = DiscountInfo(0.05f, 15f)

    fun getApplicableDiscount(total: Float): Float {
        // Assuming store A is selected
        val discountInfo = discountStoreA
        return if (total > 200) {
            discountInfo.discountPerc * total
        } else if (total >= 100) {
            discountInfo.discountAmount
        } else 0f
    }
}