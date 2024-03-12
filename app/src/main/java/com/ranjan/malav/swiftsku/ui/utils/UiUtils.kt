package com.ranjan.malav.swiftsku.ui.utils

import android.content.res.Resources
import androidx.compose.ui.unit.Dp

object UiUtils {
    fun formatAmount(amount: Float): String {
        return "\$${String.format("%.2f", amount)}"
    }

    fun formatAmount(amount: Double): String {
        return "\$${String.format("%.2f", amount)}"
    }
}

val Int.percent: Dp
    get() = Dp((Resources.getSystem().displayMetrics.widthPixels * this / 100).toFloat())

val Float.percent: Dp
    get() = Dp((Resources.getSystem().displayMetrics.widthPixels * this / 100))