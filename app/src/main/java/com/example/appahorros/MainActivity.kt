package com.example.appahorros

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.appahorros.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Configurar Toolbar
        // Establece la Toolbar como la ActionBar de la actividad.
        setSupportActionBar(binding.toolbar)

        // 2. Configurar NavController
        // Obtiene el NavController del NavHostFragment.
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // 3. Configurar AppBarConfiguration
        // Define los destinos de nivel superior para el DrawerLayout.
        // El ícono de hamburguesa se mostrará en estos destinos.
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.calendarioFragment, R.id.metasFragment),
            binding.drawerLayout
        )

        // 4. Conectar Toolbar con NavController
        // Esto habilita el título dinámico y el botón de navegación (hamburguesa/atrás).
        setupActionBarWithNavController(navController, appBarConfiguration)

        // 5. Conectar NavigationView con NavController
        // Esto maneja la navegación cuando se hace clic en un ítem del drawer.
        binding.navView.setupWithNavController(navController)
    }

    // Maneja el clic en el botón de navegación (hamburguesa/atrás)
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    // Infla el menú de opciones (overflow) en la Toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // Maneja los clics en los ítems del menú de opciones (overflow)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_help -> {
                Toast.makeText(this, "Ayuda seleccionada", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_report_problem -> {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_EMAIL, arrayOf("soporte@appahorros.com"))
                    putExtra(Intent.EXTRA_SUBJECT, "Reporte de problema")
                    putExtra(Intent.EXTRA_TEXT, "Describe tu problema aquí...")
                }
                startActivity(Intent.createChooser(intent, "Reportar problema"))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
