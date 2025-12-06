package com.example.appahorros

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appahorros.database.AppDatabase
import com.example.appahorros.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(AppDatabase.getDatabase(requireContext()))
    }
    private val historialAdapter = HistorialAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar RecyclerView
        binding.rvHistorial.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = historialAdapter
        }

        // Observar el total de ahorros
        lifecycleScope.launch {
            mainViewModel.total.collectLatest { total ->
                binding.tvTotalAhorrado.text = String.format("$%.2f", total ?: 0.0)
            }
        }

        // Observar el último movimiento
        lifecycleScope.launch {
            mainViewModel.ultimoMovimiento.collectLatest { movimiento ->
                movimiento?.let {
                    val tipo = if (it.tipo == "Ahorro") "Aumento" else "Disminución"
                    binding.tvUltimoMovimiento.text = "Último movimiento: $tipo"
                }
            }
        }

        // Observar el historial de movimientos
        lifecycleScope.launch {
            mainViewModel.historial.collectLatest {
                historialAdapter.submitList(it)
            }
        }

        // Navegar a la pantalla de registro de movimiento
        binding.fabRegistrarMovimiento.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_registrarMovimientoFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
