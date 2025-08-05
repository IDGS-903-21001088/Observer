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

        // Botón "Comenzar a observar" - ahora redirige a AdminActivity2
        val btnComenzar = findViewById<Button>(R.id.btnComenzar)
        btnComenzar.setOnClickListener {
            startActivity(Intent(this, AdminActivity::class.java))
        }

        // Menú lateral unificado
        navView.setNavigationItemSelectedListener { item ->
            drawerLayout.closeDrawer(GravityCompat.END)
            when (item.itemId) {
                R.id.nav_init -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.nav_menu -> {
                    startActivity(Intent(this, AdminActivity::class.java))
                    true
                }
                R.id.nav_temperatura -> {
                    startActivity(Intent(this, temp_activity_admin::class.java))
                    true
                }
                R.id.nav_ruido -> {
                    startActivity(Intent(this, ruido_activity_admin::class.java))
                    true
                }

                R.id.nav_profile -> {
                    startActivity(Intent(this, activity_profile::class.java))
                    true
                }
                R.id.nav_log -> {
                    startActivity(Intent(this, login_activity::class.java))
                    true
                }
                R.id.nav_logout -> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    true
                }
                R.id.nav_ver_mas -> {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://ejemplo.com"))
                    startActivity(browserIntent)
                    true
                }
                else -> false
            }
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