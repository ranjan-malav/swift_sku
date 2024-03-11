package com.ranjan.malav.swiftsku.data.repository

import com.ranjan.malav.swiftsku.data.source.TransactionDataSource
import javax.inject.Inject


class TransactionRepository @Inject constructor(private val dataSource: TransactionDataSource) {
    fun getAllTransactions() = dataSource.getAllTransactions()
}