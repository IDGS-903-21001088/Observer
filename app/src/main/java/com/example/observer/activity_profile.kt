package com.example.observer

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class activity_profile : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var btnMenu: ImageButton
    private lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        drawerLayout = findViewById(R.id.drawer_layout_perfil)
        btnMenu = findViewById(R.id.btnMenuPerfil)
        navView = findViewById(R.id.nav_view_perfil)

        val etUsuario = findViewById<EditText>(R.id.etNuevoUsuario)
        val etContrasena = findViewById<EditText>(R.id.etNuevaContrasena)
        val btnGuardar = findViewById<Button>(R.id.btnGuardarPerfil)

        // Mostrar datos actuales
        val prefs = getSharedPreferences("loginPrefs", MODE_PRIVATE)
        etUsuario.setText(prefs.getString("usuario", "admin"))
        etContrasena.setText(prefs.getString("contrasena", "1234"))

        btnGuardar.setOnClickListener {
            val editor = prefs.edit()
            editor.putString("usuario", etUsuario.text.toString())
            editor.putString("contrasena", etContrasena.text.toString())
            editor.apply()
            Toast.makeText(this, "Datos guardados", Toast.LENGTH_SHORT).show()
        }

        btnMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.END)
        }

        navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_menu -> startActivity(Intent(this, AdminActivity::class.java))
                R.id.nav_temperatura -> startActivity(Intent(this, temp_activity::class.java))
                R.id.nav_bas -> startActivity(Intent(this, bas_activity::class.java))
                R.id.nav_ver_mas -> {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://ejemplo.com"))
                    startActivity(browserIntent)
                }
            }
            drawerLayout.closeDrawer(GravityCompat.END)
            true
        }
    }
}