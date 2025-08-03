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
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class login_activity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var toolbar: Toolbar
    private lateinit var btnMenu: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        drawerLayout = findViewById(R.id.drawer_layout_login)
        btnMenu = findViewById(R.id.btnMenuLogin)
        toolbar = findViewById(R.id.toolbar_login)
        navView = findViewById(R.id.nav_view_login)

        val etUsuario = findViewById<EditText>(R.id.etUsuario)
        val etContrasena = findViewById<EditText>(R.id.etContrasena)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val usuario = etUsuario.text.toString()
            val contrasena = etContrasena.text.toString()

            val prefs = getSharedPreferences("loginPrefs", MODE_PRIVATE)
            val usuarioGuardado = prefs.getString("usuario", "admin")
            val contrasenaGuardada = prefs.getString("contrasena", "1234")

            if (usuario == usuarioGuardado && contrasena == contrasenaGuardada) {
                val intent = Intent(this, AdminActivity2::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
            }
        }

        // Botón hamburguesa a la derecha
        btnMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.END)
        }

        // Menú lateral
        navView.setNavigationItemSelectedListener { item ->
            drawerLayout.closeDrawer(GravityCompat.END)
            when (item.itemId) {
                R.id.nav_init -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.nav_menu -> {
                    startActivity(Intent(this, AdminActivity2::class.java))
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
                R.id.nav_bas -> {
                    startActivity(Intent(this, bas_activity::class.java))
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