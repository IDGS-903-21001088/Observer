package com.example.observer

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.drawerlayout.widget.DrawerLayout
import com.example.observer.ui.theme.ObserverTheme
import com.google.android.material.navigation.NavigationView
import androidx.core.net.toUri
import androidx.core.view.GravityCompat

class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var toolbar: Toolbar
    private lateinit var btnMenu: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        toolbar = findViewById(R.id.toolbar)
        btnMenu = findViewById(R.id.btnMenu)

        setSupportActionBar(toolbar)

        // Botón hamburguesa a la derecha
        btnMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.END)
        }

        // Botón "Comenzar a observar"
        val btnComenzar = findViewById<Button>(R.id.btnComenzar)
        btnComenzar.setOnClickListener {
            startActivity(Intent(this, AdminActivity::class.java)) // Puedes cambiar la pantalla destino
        }

        // Menú lateral
        navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_init -> {
                    startActivity(Intent(this, MainActivity::class.java))
                }
                R.id.nav_menu -> {
                    startActivity(Intent(this, AdminActivity::class.java))
                }
                R.id.nav_temperatura -> {
                    startActivity(Intent(this, temp_activity::class.java))
                }
                R.id.nav_ruido -> {
                    startActivity(Intent(this, ruido_activity::class.java))
                }
                R.id.nav_log -> {
                    startActivity(Intent(this, login_activity::class.java))
                }
                R.id.nav_ver_mas -> {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://ejemplo.com"))
                    startActivity(browserIntent)
                }
            }

            drawerLayout.closeDrawer(GravityCompat.END) // Cierra el menú después de seleccionar
            true
        }
    }
}



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ObserverTheme {
        Greeting("Android")
    }
}