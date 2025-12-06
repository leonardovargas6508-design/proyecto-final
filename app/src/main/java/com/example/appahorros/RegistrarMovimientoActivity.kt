package com.example.appahorros

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.appahorros.database.AppDatabase
import com.example.appahorros.databinding.ActivityRegistrarMovimientoBinding
import com.example.appahorros.models.Movimiento
import kotlinx.coroutines.launch

class RegistrarMovimientoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrarMovimientoBinding
    private val viewModel: RegistrarMovimientoViewModel by viewModels {
        RegistrarMovimientoViewModelFactory(AppDatabase.getDatabase(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrarMovimientoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGuardar.setOnClickListener {
            if (validateInput()) {
                val tipo = if (binding.rbAhorro.isChecked) "Ahorro" else "Retiro"
                val cantidad = binding.etCantidad.text.toString().toDouble()
                val descripcion = binding.etDescripcion.text.toString()

                lifecycleScope.launch {
                    viewModel.guardarMovimiento(
                        Movimiento(
                            tipo = tipo,
                            cantidad = cantidad,
                            descripcion = descripcion,
                            fecha = System.currentTimeMillis()
                        )
                    )
                    Toast.makeText(this@RegistrarMovimientoActivity, "Movimiento guardado", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }

    private fun validateInput(): Boolean {
        var isValid = true
        binding.tvCantidadError.visibility = View.GONE

        if (binding.etCantidad.text.isNullOrEmpty()) {
            binding.tvCantidadError.text = "La cantidad no puede estar vacía"
            binding.tvCantidadError.visibility = View.VISIBLE
            isValid = false
        }
        
        return isValid
    }
}
