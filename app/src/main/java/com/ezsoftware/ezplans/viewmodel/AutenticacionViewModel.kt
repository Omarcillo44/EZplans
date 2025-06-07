package com.ezsoftware.ezplans.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ezsoftware.ezplans.models.acceso.AccesoRequest
import com.ezsoftware.ezplans.network.ApiService
import com.ezsoftware.ezplans.network.RetrofitInstance
import com.ezsoftware.ezplans.preferences.PreferenceHelper
import kotlinx.coroutines.launch

class AutenticacionViewModel(application: Application) : AndroidViewModel(application) {
    private val apiService: ApiService = RetrofitInstance.api
    private val prefs = PreferenceHelper(application)

    fun login(celular: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = apiService.login(AccesoRequest(celular, password))
                if (response.isSuccessful) {
                    val token = response.body()?.jwtToken ?: ""

                    // Guardar datos en preferencias
                    prefs.guardarNumeroCelular(celular)
                    prefs.guardarToken(token)

                    // Obtener y almacenar datos del usuario con el Bearer token
                    obtenerDatosUsuario(celular, token, onError)

                    onSuccess()
                } else {
                    onError("Error al iniciar sesión")
                }
            } catch (e: Exception) {
                onError("Error de conexión")
            }
        }
    }

    private suspend fun obtenerDatosUsuario(celularUsuario: String, token: String, onError: (String) -> Unit) {

        try {
            val response = apiService.obtenerDatosUsuario(celularUsuario, "Bearer $token")
            when {
                response.isSuccessful -> {
                    response.body()?.let { datosUsuario ->
                        // Guardar datos en preferencias
                        prefs.guardarNumeroCelular(datosUsuario.celularUsuario)
                        prefs.guardarNombreUsuario(datosUsuario.nombreUsuario)
                        prefs.guardarApellidoUsuario(datosUsuario.apellidoUsuario)
                        prefs.guardarIDUsuario(datosUsuario.idUsuario)

                        println("Preferencias guardadas")
                        println(prefs.leerIDUsuario())
                        println(prefs.leerNombreUsuario())
                        println(prefs.leerApellidoUsuario())
                    }
                }
                response.code() == 403 -> {
                    println("aki 1")
                    onError("Sesión inválida, por favor inicia sesión nuevamente.")
                }
                else -> {
                    println("aki 2")
                    onError("Error al obtener los datos del usuario.")
                }
            }
        } catch (e: Exception) {
            println("error:$e")
            onError("Error de conexión al obtener datos del usuario.")
        }
    }
}