package com.ranjan.malav.swiftsku.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ranjan.malav.swiftsku.data.model.Transaction


@Dao
interface TransactionDao {
    @Query("SELECT * FROM `transaction`")
    fun getAll(): List<Transaction>

    @Insert
    fun insertAll(vararg users: Transaction)

    @Delete
    fun delete(user: Transaction)
}