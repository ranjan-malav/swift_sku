package com.ranjan.malav.swiftsku.data.source

import com.ranjan.malav.swiftsku.data.local.dao.TransactionDao
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TransactionDataSource @Inject constructor(private val transactionDao: TransactionDao) {
    fun getAllTransactions() = transactionDao.getAll()
}