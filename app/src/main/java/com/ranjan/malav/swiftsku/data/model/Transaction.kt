package com.ranjan.malav.swiftsku.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.ranjan.malav.swiftsku.data.local.TrxItemListConverter
import java.util.Date


@Entity
data class Transaction(
    @PrimaryKey(autoGenerate = true) val txnId: Int = 0,
    @TypeConverters(TrxItemListConverter::class)
    var txnItems: List<TransactionItem>,
    var txnEndTime: Date? = null,
    var txnTotalGrandAmount: Float,
    var txnTotalDiscountAmount: Float,
    var txnTotalTaxAmount: Float,
    var txnSubTotalAmount: Float,
    var txnStatus: TransactionStatus, // COMPLETED, SAVED
    var txnStartTime: Date
)

enum class TransactionStatus {
    COMPLETED, SAVED
}
