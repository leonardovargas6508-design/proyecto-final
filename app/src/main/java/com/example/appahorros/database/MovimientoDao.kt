package com.example.appahorros.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.appahorros.models.Movimiento
import kotlinx.coroutines.flow.Flow

@Dao
interface MovimientoDao {
    @Insert
    suspend fun insertar(movimiento: Movimiento)

    @Query("SELECT SUM(CASE WHEN tipo = 'Ahorro' THEN cantidad ELSE -cantidad END) FROM movimientos")
    fun obtenerTotal(): Flow<Double?>

    @Query("SELECT * FROM movimientos ORDER BY fecha DESC")
    fun obtenerHistorial(): Flow<List<Movimiento>>
}
