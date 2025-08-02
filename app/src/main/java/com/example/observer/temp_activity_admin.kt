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

class temp_activity_admin : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var btnMenu: ImageButton
    private lateinit var navView: NavigationView

    private lateinit var btnZonaA: Button
    private lateinit var btnZonaB: Button

    // MQTT Manager
    private lateinit var mqttManager: MqttManager

    // Variables para almacenar datos reales
    private var temperaturaZonaA = 25.0
    private var temperaturaZonaB = 25.0

    // TextViews para mostrar temperaturas
    private lateinit var txtTemperaturaA: TextView
    private lateinit var txtTemperaturaB: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temp_admin)

        drawerLayout = findViewById(R.id.drawer_layout_temp_adm)
        toolbar = findViewById(R.id.toolbar_observacion)
        navView = findViewById(R.id.nav_view_temp_adm)
        btnMenu = findViewById(R.id.btnMenu)

        setSupportActionBar(toolbar)

        btnZonaA = findViewById(R.id.btnZonaA)
        btnZonaB = findViewById(R.id.btnZonaB)

        // Buscar los TextViews para mostrar temperaturas
        txtTemperaturaA = findViewById(R.id.txtTemperaturaA)
        txtTemperaturaB = findViewById(R.id.txtTemperaturaB)

        // Inicializar MQTT
        mqttManager = MqttManager(this)

        // Configurar callback para recibir datos de temperatura
        mqttManager.onTemperaturaReceived = { tempA, tempB ->
            runOnUiThread {
                temperaturaZonaA = tempA
                temperaturaZonaB = tempB
                actualizarInterfaz()
            }
        }

        // Conectar MQTT
        mqttManager.conectar()

        // Actualizar interfaz inicial
        actualizarInterfaz()

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

        val btnIrOtraPantalla = findViewById<Button>(R.id.btnIrOtraPantalla)

        btnIrOtraPantalla.setOnClickListener {
            val intent = Intent(this, activity_detalle_temp::class.java)
            startActivity(intent)
        }

        // Botón hamburguesa a la derecha
        btnMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.END)
        }

        // Navegación del menú lateral
        navView.setNavigationItemSelectedListener { item ->
            drawerLayout.closeDrawer(GravityCompat.END)
            when (item.itemId) {
                R.id.nav_menu -> {
                    startActivity(Intent(this, AdminActivity::class.java))
                    true
                }

                R.id.nav_temperatura -> {
                    startActivity(Intent(this, temp_activity::class.java))
                    true
                }

                R.id.nav_ruido -> {
                    startActivity(Intent(this, ruido_activity::class.java))
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

                // Opción: cerrar sesión
                R.id.nav_logout -> {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }
    }

    private fun actualizarInterfaz() {
        // Actualizar colores de botones según temperatura
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

        // Actualizar textos de temperatura
        txtTemperaturaA.text = "${temperaturaZonaA}°C"
        txtTemperaturaB.text = "${temperaturaZonaB}°C"
    }

    override fun onDestroy() {
        super.onDestroy()
        mqttManager.desconectar()
    }
}