package com.baquang.lab07_room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ExpenseViewModel(private val dao: ExpenseDao) : ViewModel() {

    val expenses: StateFlow<List<Expense>> = dao.observeAll().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    fun add(title: String, amountText: String, note: String) {
        val amount = amountText.toDoubleOrNull() ?: return
        if (title.trim().isEmpty() || amount <= 0.0) return

        viewModelScope.launch {
            dao.insert(
                Expense(
                    title = title.trim(),
                    amount = amount,
                    note = note.trim()
                )
            )
        }
    }

    fun remove(expense: Expense) {
        viewModelScope.launch {
            dao.delete(expense)
        }
    }

    class Factory(private val dao: ExpenseDao) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ExpenseViewModel(dao) as T
        }
    }
}