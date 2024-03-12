package com.ranjan.malav.swiftsku.data.repository

import com.ranjan.malav.swiftsku.data.local.dao.TransactionDao
import com.ranjan.malav.swiftsku.data.model.Transaction
import com.ranjan.malav.swiftsku.data.model.TransactionStatus
import javax.inject.Inject


class TransactionRepository @Inject constructor(
    private val trxDao: TransactionDao,
) {
    suspend fun getCompletedTransactions() = trxDao.getCompletedTransactions(TransactionStatus.COMPLETED)

    suspend fun insert(trx: Transaction) = trxDao.insertAll(trx)

    suspend fun upsert(trx: Transaction) = trxDao.upsert(trx)

    suspend fun findTrxByStatus(status: TransactionStatus) = trxDao.findTrxByStatus(status)
}