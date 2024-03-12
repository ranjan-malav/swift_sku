package com.ranjan.malav.swiftsku.ui.utils

import android.content.res.Resources
import androidx.compose.ui.unit.Dp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object UiUtils {
    fun formatAmount(amount: Float): String {
        return "\$${String.format("%.2f", amount)}"
    }

    fun formatAmount(amount: Double): String {
        return "\$${String.format("%.2f", amount)}"
    }

    fun formatDate(date: Date): String {
        val calendar = Calendar.getInstance()
        calendar.time = date
        val formatter = SimpleDateFormat("MMM d, yyyy HH:mm a", Locale.ROOT)
        return formatter.format(calendar.time)
    }
}

val Int.percent: Dp
    get() = Dp((Resources.getSystem().displayMetrics.heightPixels * this / 100).toFloat())

val Float.percent: Dp
    get() = Dp((Resources.getSystem().displayMetrics.heightPixels * this / 100))