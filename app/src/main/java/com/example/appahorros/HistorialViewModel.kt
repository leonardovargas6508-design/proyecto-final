package com.example.appahorros

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appahorros.database.AppDatabase
import com.example.appahorros.models.Movimiento
import kotlinx.coroutines.flow.Flow

class HistorialViewModel(private val database: AppDatabase) : ViewModel() {

    val historial: Flow<List<Movimiento>> = database.movimientoDao().obtenerHistorial()
}

class HistorialViewModelFactory(private val database: AppDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistorialViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HistorialViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
