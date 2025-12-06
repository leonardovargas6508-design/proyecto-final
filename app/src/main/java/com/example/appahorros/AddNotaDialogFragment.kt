package com.example.appahorros

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.appahorros.databinding.DialogAddNotaBinding
import com.example.appahorros.models.Nota

class AddNotaDialogFragment : DialogFragment() {

    private var _binding: DialogAddNotaBinding? = null
    private val binding get() = _binding!!

    private val calendarioViewModel: CalendarioViewModel by activityViewModels()
    private var fechaSeleccionada: Long = System.currentTimeMillis()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogAddNotaBinding.inflate(layoutInflater)

        arguments?.let {
            fechaSeleccionada = it.getLong(ARG_FECHA)
        }

        return AlertDialog.Builder(requireContext())
            .setTitle("Agregar Nota")
            .setView(binding.root)
            .setPositiveButton("Guardar") { _, _ ->
                val textoNota = binding.etNota.text.toString()
                if (textoNota.isNotBlank()) {
                    calendarioViewModel.agregarNota(Nota(fechaMillis = fechaSeleccionada, texto = textoNota))
                }
            }
            .setNegativeButton("Cancelar", null)
            .create()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_FECHA = "fecha"

        fun newInstance(fecha: Long): AddNotaDialogFragment {
            val fragment = AddNotaDialogFragment()
            val args = Bundle()
            args.putLong(ARG_FECHA, fecha)
            fragment.arguments = args
            return fragment
        }
    }
}
