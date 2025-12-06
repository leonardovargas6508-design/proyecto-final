package com.example.appahorros

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appahorros.adapters.NotasCalendarioAdapter
import com.example.appahorros.databinding.FragmentCalendarioBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

class CalendarioFragment : Fragment() {

    private var _binding: FragmentCalendarioBinding? = null
    private val binding get() = _binding!!

    private val calendarioViewModel: CalendarioViewModel by activityViewModels()
    private lateinit var notasAdapter: NotasCalendarioAdapter
    private var fechaSeleccionada: Long = System.currentTimeMillis()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        updateFechaSeleccionadaUI()

        calendarioViewModel.notas.observe(viewLifecycleOwner) {
            updateNotasAdapter()
        }

        binding.btnSeleccionarFecha.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Seleccionar Fecha")
                .setSelection(fechaSeleccionada)
                .build()

            datePicker.addOnPositiveButtonClickListener {
                fechaSeleccionada = it
                updateFechaSeleccionadaUI()
                updateNotasAdapter()
            }

            datePicker.show(childFragmentManager, "DATE_PICKER")
        }

        binding.btnAgregarNota.setOnClickListener {
            val dialog = AddNotaDialogFragment.newInstance(fechaSeleccionada)
            dialog.show(childFragmentManager, "ADD_NOTA_DIALOG")
        }
    }

    private fun setupRecyclerView() {
        notasAdapter = NotasCalendarioAdapter { nota ->
            calendarioViewModel.eliminarNota(nota)
        }
        binding.rvNotas.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = notasAdapter
        }
    }

    private fun updateFechaSeleccionadaUI() {
        val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        binding.tvFechaSeleccionada.text = sdf.format(Date(fechaSeleccionada))
    }

    private fun updateNotasAdapter() {
        notasAdapter.submitList(calendarioViewModel.getNotasForDate(fechaSeleccionada))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
