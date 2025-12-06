package com.example.appahorros

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.appahorros.database.AppDatabase
import com.example.appahorros.databinding.FragmentRegistrarMovimientoBinding
import com.example.appahorros.models.Movimiento
import kotlinx.coroutines.launch

class RegistrarMovimientoFragment : Fragment() {

    private var _binding: FragmentRegistrarMovimientoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegistrarMovimientoViewModel by viewModels {
        RegistrarMovimientoViewModelFactory(AppDatabase.getDatabase(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrarMovimientoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                    Toast.makeText(requireContext(), "Movimiento guardado", Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                }
            }
        }
    }

    private fun validateInput(): Boolean {
        var isValid = true
        binding.tilCantidad.error = null

        if (binding.etCantidad.text.isNullOrEmpty()) {
            binding.tilCantidad.error = "La cantidad no puede estar vacía"
            isValid = false
        }

        return isValid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
