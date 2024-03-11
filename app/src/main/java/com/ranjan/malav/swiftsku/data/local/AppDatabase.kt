package com.ranjan.malav.swiftsku.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ranjan.malav.swiftsku.data.dao.TransactionDao
import com.ranjan.malav.swiftsku.data.model.Transaction


@Database(entities = [Transaction::class], version = 1)
@TypeConverters(FloatListConverter::class, DateConverters::class, TrxItemListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "swift-sku"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                instance
            }
        }
    }
}