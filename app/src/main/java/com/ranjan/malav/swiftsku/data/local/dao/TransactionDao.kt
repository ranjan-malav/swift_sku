package com.ranjan.malav.swiftsku.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.ranjan.malav.swiftsku.data.model.Transaction
import com.ranjan.malav.swiftsku.data.model.TransactionStatus


@Dao
interface TransactionDao {
    @Query("SELECT * FROM `transaction`")
    suspend fun getAll(): List<Transaction>

    @Query("SELECT * from `transaction` WHERE txnStatus = :status")
    suspend fun getCompletedTransactions(status: TransactionStatus): List<Transaction>

    @Insert
    suspend fun insertAll(vararg trxs: Transaction)

    @Upsert
    suspend fun upsert(trx: Transaction)

    @Query("SELECT * from `transaction` WHERE txnStatus = :status")
    suspend fun findTrxByStatus(status: TransactionStatus): Transaction?
}