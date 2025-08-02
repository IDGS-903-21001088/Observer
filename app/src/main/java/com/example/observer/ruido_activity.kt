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

class ruido_activity : AppCompatActivity() {


    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var btnMenu: ImageButton
    private lateinit var navView: NavigationView

    private lateinit var btnZonaA: Button
    private lateinit var btnZonaB: Button
    private lateinit var txtZona: TextView
    private lateinit var txtSensor: TextView

    private lateinit var mqttManager: MqttManager

    // Variables para almacenar datos reales de ruido (en dB)
    private var ruidoZonaA = 45.0
    private var ruidoZonaB = 45.0

    // TextViews para mostrar niveles de ruido
    private lateinit var txtRuidoA: TextView
    private lateinit var txtRuidoB: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ruido_admin)

        drawerLayout = findViewById(R.id.drawer_layout_rdad)
        toolbar = findViewById(R.id.toolbar_observacion)
        navView = findViewById(R.id.nav_view_rdad)
        btnMenu = findViewById(R.id.btnMenu)

        setSupportActionBar(toolbar)

        btnZonaA = findViewById(R.id.btnZonaA)
        btnZonaB = findViewById(R.id.btnZonaB)

        // Buscar los TextViews para mostrar ruido (debes agregarlos en tu layout)
        txtRuidoA = findViewById(R.id.txtRuidoA)
        txtRuidoB = findViewById(R.id.txtRuidoB)

        // Inicializar MQTT
        mqttManager = MqttManager(this)

        // Configurar callback para recibir datos de ruido
        mqttManager.onRuidoReceived = { ruidoA, ruidoB ->
            runOnUiThread {
                ruidoZonaA = ruidoA
                ruidoZonaB = ruidoB
                actualizarInterfazRuido()
            }
        }

        mqttManager.conectar()

        // Actualizar interfaz inicial
        actualizarInterfazRuido()

        // Al hacer clic en zona A
        btnZonaA.setOnClickListener {
            txtZona.text = "Zona: A"
            txtSensor.text = "Sensor: Micrófono-A"
        }

        // Al hacer clic en zona B
        btnZonaB.setOnClickListener {
            txtZona.text = "Zona: B"
            txtSensor.text = "Sensor: Micrófono-B"
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

    private fun actualizarInterfazRuido() {
        // Actualizar colores de botones según nivel de ruido (>60dB = ruidoso)
        if (ruidoZonaA > 60.0) {
            btnZonaA.setBackgroundResource(R.drawable.fondo_redondo_rojo)
        } else {
            btnZonaA.setBackgroundResource(R.drawable.fondo_redondo_verde)
        }

        if (ruidoZonaB > 60.0) {
            btnZonaB.setBackgroundResource(R.drawable.fondo_redondo_rojo)
        } else {
            btnZonaB.setBackgroundResource(R.drawable.fondo_redondo_verde)
        }

        // Actualizar textos de ruido
        txtRuidoA.text = "${ruidoZonaA} dB"
        txtRuidoB.text = "${ruidoZonaB} dB"
    }

    override fun onDestroy() {
        super.onDestroy()
        mqttManager.desconectar()
    }
}