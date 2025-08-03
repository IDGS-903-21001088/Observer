package com.example.observer

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
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

class activity_detalle_temp : AppCompatActivity() {

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
        setContentView(R.layout.activity_detalle_temp)

        btnMenu = findViewById(R.id.btnMenu)
        drawerLayout = findViewById(R.id.drawer_layout_dt_temp)
        toolbar = findViewById(R.id.toolbar_observacion)
        navView = findViewById(R.id.nav_view_dt_temp)

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

        // Referencias a los layouts de cada zona
        val layoutInfoA = findViewById<LinearLayout>(R.id.layoutInfoZonaA)
        val layoutInfoB = findViewById<LinearLayout>(R.id.layoutInfoZonaB)

        // Mostrar por defecto la info de Zona A
        layoutInfoA.visibility = View.VISIBLE
        layoutInfoB.visibility = View.GONE

        // Referencias a botones de aspersores
        val btnEncenderZonaA = findViewById<Button>(R.id.btnEncenderZonaA)
        val btnApagarZonaA = findViewById<Button>(R.id.btnApagarZonaA)
        val btnEncenderZonaB = findViewById<Button>(R.id.btnEncenderZonaB)
        val btnApagarZonaB = findViewById<Button>(R.id.btnApagarZonaB)

        // Botones en el mapa
        val btnZonaA = findViewById<Button>(R.id.btnZonaA)
        val btnZonaB = findViewById<Button>(R.id.btnZonaB)

        // Al presionar Zona A
        btnZonaA.setOnClickListener {
            layoutInfoA.visibility = View.VISIBLE
            layoutInfoB.visibility = View.GONE
        }

        // Al presionar Zona B
        btnZonaB.setOnClickListener {
            layoutInfoA.visibility = View.GONE
            layoutInfoB.visibility = View.VISIBLE
        }

        // Funcionalidad de botones
        btnEncenderZonaA.setOnClickListener {
            Toast.makeText(this, "Aspersores de Zona A ENCENDIDOS", Toast.LENGTH_SHORT).show()
            // Aquí puedes agregar lógica para activar el aspersor


        }

        btnApagarZonaA.setOnClickListener {
            Toast.makeText(this, "Aspersores de Zona A APAGADOS", Toast.LENGTH_SHORT).show()
        }

        btnEncenderZonaB.setOnClickListener {
            Toast.makeText(this, "Aspersores de Zona B ENCENDIDOS", Toast.LENGTH_SHORT).show()
            // Aquí puedes agregar lógica para activar el aspersor


        }

        btnApagarZonaB.setOnClickListener {
            Toast.makeText(this, "Aspersores de Zona B APAGADOS", Toast.LENGTH_SHORT).show()
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

    }
}
