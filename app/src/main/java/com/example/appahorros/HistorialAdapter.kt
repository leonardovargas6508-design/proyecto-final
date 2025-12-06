package com.example.appahorros

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.appahorros.databinding.ItemMovimientoBinding
import com.example.appahorros.models.Movimiento
import java.text.SimpleDateFormat
import java.util.*

class HistorialAdapter : ListAdapter<Movimiento, HistorialAdapter.MovimientoViewHolder>(MovimientoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
        MovimientoViewHolder {
        val binding = ItemMovimientoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovimientoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovimientoViewHolder, position: Int) {
        val movimiento = getItem(position)
        holder.bind(movimiento)
    }

    inner class MovimientoViewHolder(private val binding: ItemMovimientoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movimiento: Movimiento) {
            binding.tvCantidadItem.text = String.format("$%.2f", movimiento.cantidad)
            binding.tvTipoItem.text = movimiento.tipo
            binding.tvFechaItem.text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(movimiento.fecha))
            binding.tvDescripcionItem.text = movimiento.descripcion

            if (movimiento.tipo == "Ahorro") {
                binding.tvCantidadItem.setTextColor(Color.GREEN)
            } else {
                binding.tvCantidadItem.setTextColor(Color.RED)
            }
        }
    }

    class MovimientoDiffCallback : DiffUtil.ItemCallback<Movimiento>() {
        override fun areItemsTheSame(oldItem: Movimiento, newItem: Movimiento): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movimiento, newItem: Movimiento): Boolean {
            return oldItem == newItem
        }
    }
}