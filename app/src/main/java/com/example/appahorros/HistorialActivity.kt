package com.example.appahorros

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appahorros.database.AppDatabase
import com.example.appahorros.databinding.ActivityHistorialBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HistorialActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistorialBinding
    private val historialViewModel: HistorialViewModel by viewModels {
        HistorialViewModelFactory(AppDatabase.getDatabase(this))
    }
    private val historialAdapter = HistorialAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistorialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvHistorial.apply {
            layoutManager = LinearLayoutManager(this@HistorialActivity)
            adapter = historialAdapter
        }

        lifecycleScope.launch {
            historialViewModel.historial.collectLatest {
                historialAdapter.submitList(it)
            }
        }
    }
}
