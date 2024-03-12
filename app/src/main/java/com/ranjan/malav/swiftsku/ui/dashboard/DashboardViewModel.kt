package com.ranjan.malav.swiftsku.ui.dashboard

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject


@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val trxRepo: TransactionRepository,
    private val priceBookRepo: PriceBookRepository
) : ViewModel() {

    private var _bookItems = MutableStateFlow<List<PriceBookItem>>(emptyList())
    var bookItems: StateFlow<List<PriceBookItem>> = _bookItems

    private var selectedItems = arrayListOf<TransactionItem>()
    private var _selectedItems = MutableStateFlow(selectedItems)
    var selectedItemsLive: StateFlow<ArrayList<TransactionItem>> = _selectedItems

    private var _totals = MutableStateFlow(Totals())
    var totals: StateFlow<Totals> = _totals

    private var savedTrx: Transaction? = null
    private var trxStartTime: Date? = null

    private var _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    var transactions: StateFlow<List<Transaction>> = _transactions

    fun getTransactions() = viewModelScope.launch(Dispatchers.IO) {
        val trxs = trxRepo.getAllTransactions()
        withContext(Dispatchers.Main) {
            _transactions.value = trxs
        }
    }

    fun getPriceBookData() = viewModelScope.launch(Dispatchers.IO) {
        val bookItems = priceBookRepo.getPriceBook()
        withContext(Dispatchers.Main) {
            _bookItems.value = bookItems
        }
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
        withContext(Dispatchers.Main) {
            _selectedItems.value = selectedItems
            _totals.value = Totals()
        }
    }

    fun recallTransaction() = viewModelScope.launch(Dispatchers.IO) {
        val savedTrx = trxRepo.findTrxByStatus(TransactionStatus.SAVED)
        // If there is any saved trx, replace it's content
        savedTrx?.let {
            this@DashboardViewModel.savedTrx = it
            selectedItems.addAll(it.txnItems)
            withContext(Dispatchers.Main) {
                _selectedItems.value = selectedItems
            }
        }
        val totals = calculateTotals()
        withContext(Dispatchers.Main) {
            _totals.value = totals
        }
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
        withContext(Dispatchers.Main) {
            _selectedItems.value = selectedItems
            _totals.value = Totals()
        }
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