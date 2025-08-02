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

class bas_activity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var btnMenu: ImageButton
    private lateinit var navView: NavigationView

    private lateinit var btnZonaA: Button
    private lateinit var btnZonaB: Button
    private lateinit var txtZona: TextView
    private lateinit var txtSensor: TextView

    private lateinit var mqttManager: MqttManager

    // Variables para almacenar datos reales de presión (nivel de llenado en %)
    private var presionZonaA = 30.0
    private var presionZonaB = 30.0

    // TextViews para mostrar nivel de llenado
    private lateinit var txtPresionA: TextView
    private lateinit var txtPresionB: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bas)

        btnMenu = findViewById(R.id.btnMenu)
        drawerLayout = findViewById(R.id.drawer_layout_bas)
        toolbar = findViewById(R.id.toolbar_observacion)
        navView = findViewById(R.id.nav_view_bas)

        setSupportActionBar(toolbar)

        btnZonaA = findViewById(R.id.btnZonaA)
        btnZonaB = findViewById(R.id.btnZonaB)

        txtPresionA = findViewById(R.id.txtPresionA)
        txtPresionB = findViewById(R.id.txtPresionB)

        // Inicializar MQTT
        mqttManager = MqttManager(this)

        // Configurar callback para recibir datos de presión
        mqttManager.onPresionReceived = { presionA, presionB ->
            runOnUiThread {
                presionZonaA = presionA
                presionZonaB = presionB
                actualizarInterfazBasura()
            }
        }

        mqttManager.conectar()

        actualizarInterfazBasura()

        // Al hacer clic en zona A
        btnZonaA.setOnClickListener {
            txtZona.text = "Zona: A"
            txtSensor.text = "Sensor: Ultrasonico-A"
        }

        // Al hacer clic en zona B
        btnZonaB.setOnClickListener {
            txtZona.text = "Zona: B"
            txtSensor.text = "Sensor: Ultrasonico-B"
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

                // Oción: cerrar sesión
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

    private fun actualizarInterfazBasura() {
        // Actualizar colores de botones según nivel de llenado (>80% = lleno)
        if (presionZonaA > 80.0) {
            btnZonaA.setBackgroundResource(R.drawable.fondo_redondo_rojo)
        } else {
            btnZonaA.setBackgroundResource(R.drawable.fondo_redondo_verde)
        }

        if (presionZonaB > 80.0) {
            btnZonaB.setBackgroundResource(R.drawable.fondo_redondo_rojo)
        } else {
            btnZonaB.setBackgroundResource(R.drawable.fondo_redondo_verde)
        }

        // Actualizar textos de nivel de llenado
        txtPresionA.text = "${presionZonaA.toInt()}% lleno"
        txtPresionB.text = "${presionZonaB.toInt()}% lleno"
    }

    override fun onDestroy() {
        super.onDestroy()
        mqttManager.desconectar()
    }
}