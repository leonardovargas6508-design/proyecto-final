package com.example.appahorros.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.appahorros.databinding.ItemNotaBinding
import com.example.appahorros.models.Nota

class NotasCalendarioAdapter(private val onDeleteClick: (Nota) -> Unit) :
    ListAdapter<Nota, NotasCalendarioAdapter.NotaViewHolder>(NotaDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            NotaViewHolder {
        val binding = ItemNotaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotaViewHolder, position: Int) {
        val nota = getItem(position)
        holder.bind(nota)
    }

    inner class NotaViewHolder(private val binding: ItemNotaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(nota: Nota) {
            binding.tvNotaTexto.text = nota.texto
            binding.btnBorrarNota.setOnClickListener { onDeleteClick(nota) }
        }
    }

    class NotaDiffCallback : DiffUtil.ItemCallback<Nota>() {
        override fun areItemsTheSame(oldItem: Nota, newItem: Nota): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Nota, newItem: Nota): Boolean {
            return oldItem == newItem
        }
    }
}
