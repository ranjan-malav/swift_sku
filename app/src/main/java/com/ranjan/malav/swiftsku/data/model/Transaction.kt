package com.ranjan.malav.swiftsku.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.ranjan.malav.swiftsku.data.local.TrxItemListConverter
import java.util.Date


@Entity
data class Transaction(
    @PrimaryKey val txnId: String,
    @TypeConverters(TrxItemListConverter::class)
    val txnItems: List<TransactionItem>,
    val txnEndTime: Date,
    val txnTotalGrandAmount: Float,
    val txnTotalDiscountAmount: Float,
    val txnTotalTaxAmount: Float,
    val txnSubTotalAmount: Float,
    val txnStatus: TransactionStatus, // COMPLETED, SAVED
    val txnStartTime: Date
)

enum class TransactionStatus {
    COMPLETED, SAVED
}
