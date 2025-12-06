package com.example.appahorros

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appahorros.models.Nota
import java.util.Calendar

class CalendarioViewModel : ViewModel() {

    private val _notas = MutableLiveData<MutableList<Nota>>(mutableListOf())
    val notas: LiveData<MutableList<Nota>> = _notas

    fun agregarNota(nota: Nota) {
        val currentList = _notas.value ?: mutableListOf()
        currentList.add(nota)
        _notas.value = currentList
    }

    fun eliminarNota(nota: Nota) {
        val currentList = _notas.value ?: mutableListOf()
        currentList.remove(nota)
        _notas.value = currentList
    }

    fun getNotasForDate(fechaMillis: Long): List<Nota> {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = fechaMillis
        val startOfDay = calendar.apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        val endOfDay = calendar.apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }.timeInMillis

        return _notas.value?.filter { it.fechaMillis in startOfDay..endOfDay } ?: emptyList()
    }
}
