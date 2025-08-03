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

class AdminActivity2 : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var btnMenu: ImageButton
    private lateinit var toolbar: Toolbar
    private lateinit var navView: NavigationView
    private lateinit var btnIrTemperatura: Button
    private lateinit var btnIrRuido: Button
    private lateinit var btnIrBas: Button

    // MQTT Manager
    private lateinit var mqttManager: MqttManager

    // Variables para almacenar datos
    private var temperaturaA = 25.0
    private var temperaturaB = 25.0
    private var ruidoA = 45.0
    private var ruidoB = 45.0
    private var presionA = 30.0
    private var presionB = 30.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin2)

        drawerLayout = findViewById(R.id.drawer_layout_observacion)
        btnMenu = findViewById(R.id.btnMenuObservacion)
        toolbar = findViewById(R.id.toolbar_observacion)
        navView = findViewById(R.id.nav_view)
        btnIrTemperatura = findViewById(R.id.btnIrTemperatura)
        btnIrRuido = findViewById(R.id.btnIrRuido)
        btnIrBas = findViewById(R.id.btnIrBas)

        setSupportActionBar(toolbar)

        // Inicializar MQTT
        mqttManager = MqttManager(this)

        // Configurar callbacks para recibir todos los datos
        mqttManager.onTemperaturaReceived = { tempA, tempB ->
            runOnUiThread {
                temperaturaA = tempA
                temperaturaB = tempB
                actualizarDashboard()
            }
        }

        mqttManager.onRuidoReceived = { ruidoA_new, ruidoB_new ->
            runOnUiThread {
                ruidoA = ruidoA_new
                ruidoB = ruidoB_new
                actualizarDashboard()
            }
        }

        mqttManager.onPresionReceived = { presionA_new, presionB_new ->
            runOnUiThread {
                presionA = presionA_new
                presionB = presionB_new
                actualizarDashboard()
            }
        }

        // Conectar MQTT
        mqttManager.conectar()

        // Ir a pantalla de temperatura
        btnIrTemperatura.setOnClickListener {
            startActivity(Intent(this, temp_activity_admin::class.java))
        }

        // Ir a pantalla de ruido
        btnIrRuido.setOnClickListener {
            startActivity(Intent(this, ruido_activity_admin::class.java))
        }

        // Ir a pantalla de botes de basura
        btnIrBas.setOnClickListener {
            startActivity(Intent(this, bas_activity::class.java))
        }

        // Botón hamburguesa a la derecha
        btnMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.END)
        }

        // Navegación del menú lateral
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

        // Actualizar dashboard inicial
        actualizarDashboard()
    }

    private fun actualizarDashboard() {
        // Referencias a los contenedores de texto
        val txtSensoresTemperatura = findViewById<TextView>(R.id.txtSensoresTemperatura)
        val txtAlertasTemperatura = findViewById<TextView>(R.id.txtAlertasTemperatura)

        val txtSensoresRuido = findViewById<TextView>(R.id.txtSensoresRuido)
        val txtAlertasRuido = findViewById<TextView>(R.id.txtAlertasRuido)

        val txtSensoresPresion = findViewById<TextView>(R.id.txtSensoresPresion)
        val txtAlertasPresion = findViewById<TextView>(R.id.txtAlertasPresion)

        // Contar sensores activos y alertas
        val sensoresTemp = 2 // Zona A y B
        var alertasTemp = 0
        if (temperaturaA > 30.0) alertasTemp++
        if (temperaturaB > 30.0) alertasTemp++

        val sensoresRuido = 2 // Zona A y B
        var alertasRuido = 0
        if (ruidoA > 60.0) alertasRuido++
        if (ruidoB > 60.0) alertasRuido++

        val sensoresPresion = 2 // Zona A y B
        var alertasPresion = 0
        if (presionA > 80.0) alertasPresion++
        if (presionB > 80.0) alertasPresion++

        // Mostrar los datos en los contenedores
        txtSensoresTemperatura.text = "Sensores de Temperatura: $sensoresTemp"
        txtAlertasTemperatura.text = "Alertas de Temperatura: $alertasTemp"

        txtSensoresRuido.text = "Sensores de Ruido: $sensoresRuido"
        txtAlertasRuido.text = "Alertas de Ruido: $alertasRuido"

        txtSensoresPresion.text = "Sensores de Presión: $sensoresPresion"
        txtAlertasPresion.text = "Alertas de Presión: $alertasPresion"
    }

    override fun onDestroy() {
        super.onDestroy()
        mqttManager.desconectar()
    }
}