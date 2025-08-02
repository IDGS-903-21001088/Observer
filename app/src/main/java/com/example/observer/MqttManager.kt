package com.example.observer

import android.content.Context
import org.eclipse.paho.client.mqttv3.*
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence

class MqttManager(private val context: Context) {

    private var mqttClient: MqttClient? = null
    private val serverUri = "tcp://broker.hivemq.com:1883" // cambiar por el broker de nosotros
    private val clientId = "AndroidClient_${System.currentTimeMillis()}"

    // Callbacks para recibir datos
    var onTemperaturaReceived: ((Double, Double) -> Unit)? = null
    var onRuidoReceived: ((Double, Double) -> Unit)? = null
    var onPresionReceived: ((Double, Double) -> Unit)? = null

    fun conectar() {
        try {
            val persistence = MemoryPersistence()
            mqttClient = MqttClient(serverUri, clientId, persistence)

            val connOpts = MqttConnectOptions()
            connOpts.isCleanSession = true

            mqttClient?.setCallback(object : MqttCallback {
                override fun connectionLost(cause: Throwable?) {
                    // Conexión perdida
                }

                override fun messageArrived(topic: String?, message: MqttMessage?) {
                    val payload = message?.toString() ?: return

                    when (topic) {
                        "sensor/temperatura" -> {
                            try {
                                val datos = payload.split(",")
                                val tempA = datos[0].toDouble()
                                val tempB = datos[1].toDouble()
                                onTemperaturaReceived?.invoke(tempA, tempB)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                        "sensor/ruido" -> {
                            try {
                                val datos = payload.split(",")
                                val ruidoA = datos[0].toDouble()
                                val ruidoB = datos[1].toDouble()
                                onRuidoReceived?.invoke(ruidoA, ruidoB)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                        "sensor/presion" -> {
                            try {
                                val datos = payload.split(",")
                                val presionA = datos[0].toDouble()
                                val presionB = datos[1].toDouble()
                                onPresionReceived?.invoke(presionA, presionB)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }
                }

                override fun deliveryComplete(token: IMqttDeliveryToken?) {
                    // Entrega completa
                }
            })

            mqttClient?.connect(connOpts)

            // Suscribirse a los temas
            mqttClient?.subscribe("sensor/temperatura")
            mqttClient?.subscribe("sensor/ruido")
            mqttClient?.subscribe("sensor/presion")

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun desconectar() {
        try {
            mqttClient?.disconnect()
            mqttClient?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun publicar(topic: String, mensaje: String) {
        try {
            val message = MqttMessage(mensaje.toByteArray())
            mqttClient?.publish(topic, message)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}