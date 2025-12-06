package com.example.appahorros

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.appahorros.database.AppDatabase
import com.example.appahorros.models.Movimiento
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MainViewModel(private val database: AppDatabase) : ViewModel() {

    val total: Flow<Double?> = database.movimientoDao().obtenerTotal()
    val ultimoMovimiento: Flow<Movimiento?> = database.movimientoDao().obtenerHistorial().map { it.firstOrNull() }
    val historial: Flow<List<Movimiento>> = database.movimientoDao().obtenerHistorial()

}

class MainViewModelFactory(private val database: AppDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
