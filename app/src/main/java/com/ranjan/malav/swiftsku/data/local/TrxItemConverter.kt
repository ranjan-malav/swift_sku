package com.ranjan.malav.swiftsku.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ranjan.malav.swiftsku.data.model.TransactionItem


class TrxItemListConverter {

    @TypeConverter
    fun fromJson(json: String): List<TransactionItem> {
        val type = object : TypeToken<List<TransactionItem>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJson(trxItems: List<TransactionItem>): String {
        return Gson().toJson(trxItems)
    }
}