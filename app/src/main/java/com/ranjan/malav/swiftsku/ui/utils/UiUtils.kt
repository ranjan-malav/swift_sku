package com.ranjan.malav.swiftsku.ui.utils

object UiUtils {
    fun formatAmount(amount: Float): String {
        return "\$${String.format("%.2f", amount)}"
    }

    fun formatAmount(amount: Double): String {
        return "\$${String.format("%.2f", amount)}"
    }
}