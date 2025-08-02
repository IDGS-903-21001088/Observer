package com.example.observer

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.compose.ui.graphics.Color
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class temp_activity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var btnMenu: ImageButton
    private lateinit var navView: NavigationView

    private lateinit var btnZonaA: Button
    private lateinit var btnZonaB: Button
    private lateinit var txtZona: TextView
    private lateinit var txtSensor: TextView

    // Ejemplo de valores simulados
    private var temperaturaZonaA = 32.0
    private var temperaturaZonaB = 24.5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temp)

        drawerLayout = findViewById(R.id.drawer_layout_temp)
        toolbar = findViewById(R.id.toolbar_observacion)
        navView = findViewById(R.id.nav_view_temp)

        setSupportActionBar(toolbar)

        btnZonaA = findViewById(R.id.btnZonaA)
        btnZonaB = findViewById(R.id.btnZonaB)



        // Cambiar fondo con drawables personalizados según temperatura
        if (temperaturaZonaA > 30.0) {
            btnZonaA.setBackgroundResource(R.drawable.fondo_redondo_rojo)
        } else {
            btnZonaA.setBackgroundResource(R.drawable.fondo_redondo_verde)
        }

        if (temperaturaZonaB > 30.0) {
            btnZonaB.setBackgroundResource(R.drawable.fondo_redondo_rojo)
        } else {
            btnZonaB.setBackgroundResource(R.drawable.fondo_redondo_verde)
        }



        // Al hacer clic, mostrar datos
        btnZonaA.setOnClickListener {
            txtZona.text = "Zona: A"
            txtSensor.text = "Sensor: DHT11-A"
        }

        btnZonaB.setOnClickListener {
            txtZona.text = "Zona: B"
            txtSensor.text = "Sensor: DHT11-B"
        }



        // Menú hamburguesa
        btnMenu = findViewById(R.id.btnMenuObservacion)
        btnMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.END)
        }


        // Referencias a los contenedores de información
        val layoutInfoA = findViewById<LinearLayout>(R.id.layoutInfoZonaA)
        val layoutInfoB = findViewById<LinearLayout>(R.id.layoutInfoZonaB)

        // Mostrar por defecto la info de Zona A
        layoutInfoA.visibility = View.VISIBLE
        layoutInfoB.visibility = View.GONE

        // Oculta info B y muestra info A al presionar A
        btnZonaA.setOnClickListener {
            layoutInfoA.visibility = View.VISIBLE
            layoutInfoB.visibility = View.GONE
        }

        // Oculta info A y muestra info B al presionar B
        btnZonaB.setOnClickListener {
            layoutInfoA.visibility = View.GONE
            layoutInfoB.visibility = View.VISIBLE
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