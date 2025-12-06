package com.example.appahorros

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.appahorros.database.AppDatabase
import com.example.appahorros.models.Movimiento
import kotlinx.coroutines.launch

class RegistrarMovimientoViewModel(private val database: AppDatabase) : ViewModel() {

    fun guardarMovimiento(movimiento: Movimiento) {
        viewModelScope.launch {
            database.movimientoDao().insertar(movimiento)
        }
    }
}

class RegistrarMovimientoViewModelFactory(private val database: AppDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegistrarMovimientoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegistrarMovimientoViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
