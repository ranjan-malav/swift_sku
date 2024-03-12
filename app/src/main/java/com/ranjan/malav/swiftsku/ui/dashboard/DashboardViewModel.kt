package com.ranjan.malav.swiftsku.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ranjan.malav.swiftsku.data.model.PriceBookItem
import com.ranjan.malav.swiftsku.data.model.Transaction
import com.ranjan.malav.swiftsku.data.model.TransactionItem
import com.ranjan.malav.swiftsku.data.model.TransactionStatus
import com.ranjan.malav.swiftsku.data.repository.PriceBookRepository
import com.ranjan.malav.swiftsku.data.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject


@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val trxRepo: TransactionRepository,
    private val priceBookRepo: PriceBookRepository
) : ViewModel() {

    private var _bookItems = MutableLiveData<List<PriceBookItem>>()
    var bookItems: LiveData<List<PriceBookItem>> = _bookItems

    private var selectedItems = arrayListOf<TransactionItem>()
    private var _selectedItems = MutableLiveData<ArrayList<TransactionItem>>()
    var selectedItemsLive: LiveData<ArrayList<TransactionItem>> = _selectedItems

    private var _totals = MutableLiveData(Totals())
    var totals: LiveData<Totals> = _totals

    private var savedTrx: Transaction? = null
    private var trxStartTime: Date? = null

    private var _transactions = MutableLiveData<List<Transaction>>()
    var transactions: LiveData<List<Transaction>> = _transactions

    fun getTransactions() = viewModelScope.launch(Dispatchers.IO) {
        _transactions.postValue(trxRepo.getAllTransactions())
    }

    fun getPriceBookData() = viewModelScope.launch(Dispatchers.IO) {
        _bookItems.postValue(priceBookRepo.getPriceBook())
    }

    fun selectItem(item: PriceBookItem) {
        if (trxStartTime == null) trxStartTime = Date()
        val existing = selectedItems.firstOrNull { it.pluItem == item }
        existing?.let {
            it.quantity++
        } ?: run {
            selectedItems.add(TransactionItem(item, 1))
        }
        _selectedItems.value = selectedItems
        val totals = calculateTotals()
        _totals.value = totals
    }

    fun saveTransaction() = viewModelScope.launch(Dispatchers.IO) {
        if (selectedItems.isEmpty()) return@launch
        val totals = calculateTotals()
        val savedTrx = trxRepo.findTrxByStatus(TransactionStatus.SAVED)
        // If there is any saved trx, replace it's content
        val rowId = savedTrx?.txnId ?: 0
        val trx = Transaction(
            rowId, selectedItems, null, totals.grandTotal,
            totals.discountAmount, totals.taxAmount,
            totals.subTotalAmount, TransactionStatus.SAVED,
            Date()
        )
        trxRepo.upsert(trx)
        selectedItems.clear()
        _selectedItems.postValue(selectedItems)
        _totals.postValue(Totals())
    }

    fun recallTransaction() = viewModelScope.launch(Dispatchers.IO) {
        val savedTrx = trxRepo.findTrxByStatus(TransactionStatus.SAVED)
        // If there is any saved trx, replace it's content
        savedTrx?.let {
            this@DashboardViewModel.savedTrx = it
            selectedItems.addAll(it.txnItems)
            _selectedItems.postValue(selectedItems)
        }
        val totals = calculateTotals()
        _totals.postValue(totals)
    }

    fun completeTransaction() = viewModelScope.launch(Dispatchers.IO) {
        if (selectedItems.isEmpty()) return@launch
        val totals = calculateTotals()
        val rowId = savedTrx?.txnId ?: 0
        val trx = Transaction(
            rowId, selectedItems,
            Date(),
            totals.grandTotal,
            totals.discountAmount, totals.taxAmount,
            totals.subTotalAmount, TransactionStatus.COMPLETED,
            savedTrx?.txnStartTime ?: trxStartTime ?: Date()
        )
        trxRepo.upsert(trx)
        selectedItems.clear()
        _selectedItems.postValue(selectedItems)
        _totals.postValue(Totals())
    }

    data class Totals(
        var grandTotal: Float = 0f,
        var discountAmount: Float = 0f,
        var taxAmount: Float = 0f,
        var subTotalAmount: Float = 0f
    )

    private fun calculateTotals(): Totals {
        var subTotal = 0f
        var taxTotal = 0f
        selectedItems.forEach {
            val itemTotal = it.pluItem.price * it.quantity
            subTotal += itemTotal
            taxTotal += itemTotal * (it.pluItem.taxRates.sum() / 100)
        }
        val discountTotal = DiscountEngine.getApplicableDiscount(subTotal)
        val grandTotal = subTotal + taxTotal - discountTotal
        return Totals(grandTotal, discountTotal, taxTotal, subTotal)
    }
}