package com.ranjan.malav.swiftsku.di

import android.content.Context
import com.ranjan.malav.swiftsku.data.local.AppDatabase
import com.ranjan.malav.swiftsku.data.local.dao.TransactionDao
import com.ranjan.malav.swiftsku.data.repository.PriceBookRepository
import com.ranjan.malav.swiftsku.data.repository.TransactionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return AppDatabase.getInstance(appContext)
    }

    @Singleton
    @Provides
    fun provideTransactionDao(db: AppDatabase) = db.transactionDao()

    @Provides
    @Singleton
    fun provideTransactionRepo(transactionDao: TransactionDao): TransactionRepository {
        return TransactionRepository(transactionDao)
    }

    @Provides
    @Singleton
    fun providePriceBookRepo(): PriceBookRepository {
        return PriceBookRepository()
    }
}