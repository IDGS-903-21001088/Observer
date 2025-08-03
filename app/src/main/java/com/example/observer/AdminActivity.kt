package com.example.observer

import android.content.Intent
import android.os.Bundle
import android.net.Uri
import android.view.Gravity
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView

class AdminActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var btnMenu: ImageButton
    private lateinit var toolbar: Toolbar
    private lateinit var navView: NavigationView
    private lateinit var btnIrTemperatura: Button
    private lateinit var btnIrRuido: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        drawerLayout = findViewById(R.id.drawer_layout_observacion)
        btnMenu = findViewById(R.id.btnMenuObservacion)
        toolbar = findViewById(R.id.toolbar_observacion)
        navView = findViewById(R.id.nav_view)
        btnIrTemperatura = findViewById(R.id.btnIrTemperatura)
        btnIrRuido = findViewById(R.id.btnIrRuido)

        setSupportActionBar(toolbar)

        // Botón hamburguesa a la derecha
        btnMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.END)
        }


        // Ir a pantalla de temperatura
        btnIrTemperatura.setOnClickListener {
            startActivity(Intent(this, temp_activity::class.java))
        }

        // Ir a pantalla de ruido
        btnIrRuido.setOnClickListener {
            startActivity(Intent(this, ruido_activity::class.java))
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
        // ---------------------------------------------------------------
        // BLOQUE Para Recivir datos y mostrarlos: Aquí se actualizan los datos desde sensores
        // ---------------------------------------------------------------
        val txtTemperaturaMax = findViewById<TextView>(R.id.txtTemperaturaMax)
        val txtZonaFria = findViewById<TextView>(R.id.txtZonaFria)
        val txtZonaRuidosa = findViewById<TextView>(R.id.txtZonaRuidosa)
        val txtZonaTranquila = findViewById<TextView>(R.id.txtZonaTranquila)

        // Datos de ejemplo — reemplaza esto por datos reales
        val temperaturaMaxima = 31.8
        val zonaMasFria = "Laboratorio A"
        val zonaMasRuidosa = "Pasillo Central"
        val zonaMasTranquila = "Oficina B"

        // Mostramos los valores en la interfaz
        txtTemperaturaMax.text = "Temperatura máxima: $temperaturaMaxima °C"
        txtZonaFria.text = "Zona más fría: $zonaMasFria"
        txtZonaRuidosa.text = "Zona más ruidosa: $zonaMasRuidosa"
        txtZonaTranquila.text = "Zona más tranquila: $zonaMasTranquila"
        // ---------------------------------------------------------------
    }
}