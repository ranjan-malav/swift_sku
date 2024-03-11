package com.ranjan.malav.swiftsku.ui.dashboard

import androidx.lifecycle.ViewModel
import com.ranjan.malav.swiftsku.data.repository.PriceBookRepository
import com.ranjan.malav.swiftsku.data.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val trxRepo: TransactionRepository,
    private val priceBookRepo: PriceBookRepository
) : ViewModel() {

    fun getAllTrx() {
        trxRepo.getAllTransactions()
    }

    fun getPriceBookData() {
        priceBookRepo.getPriceBook()
    }
}