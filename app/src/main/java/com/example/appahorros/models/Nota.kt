package com.example.appahorros.models

data class Nota(
    val id: Long = System.currentTimeMillis(),
    val fechaMillis: Long,
    val texto: String
)
