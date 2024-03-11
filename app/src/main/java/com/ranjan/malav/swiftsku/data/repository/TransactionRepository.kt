package com.ranjan.malav.swiftsku.data.repository

import com.ranjan.malav.swiftsku.data.dao.TransactionDao


class TransactionRepository(private val transactionDao: TransactionDao) {
    fun getTransactions() = transactionDao.getAll()
}