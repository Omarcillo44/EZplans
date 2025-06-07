package com.ezsoftware.ezplans.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ezsoftware.ezplans.models.acceso.AccesoRequest
import com.ezsoftware.ezplans.models.dashboard.datosDashboard
import com.ezsoftware.ezplans.network.ApiService
import com.ezsoftware.ezplans.network.RetrofitInstance
import com.ezsoftware.ezplans.preferences.PreferenceHelper
import kotlinx.coroutines.launch

class DashboardViewModel(application: Application) : AndroidViewModel(application) {
    private val apiService: ApiService = RetrofitInstance.api
    private val prefs = PreferenceHelper(application)

    fun obtenerDatosDashboard(
        idUsuario: Int,
        soloCompletos: Boolean?,
        esAdmin: Boolean?,
        onSuccess: (datosDashboard) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val token = prefs.leerToken() ?: ""
                val response = apiService.obtenerDatosDashboard(idUsuario, soloCompletos, esAdmin, "Bearer $token")

                if (response.isSuccessful) {
                    response.body()?.let { datosDashboard ->
                        onSuccess(datosDashboard)
                    } ?: onError("Datos vacíos en la respuesta")
                } else {
                    onError("Error ${response.code()}: ${response.message()}")
                }
            } catch (e: Exception) {
                onError("Error de conexión")
            }
        }
    }
}