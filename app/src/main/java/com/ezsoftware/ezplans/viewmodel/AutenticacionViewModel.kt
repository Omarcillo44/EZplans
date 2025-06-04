package com.ezsoftware.ezplans.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ezsoftware.ezplans.models.acceso.AccesoRequest
import com.ezsoftware.ezplans.repository.AuthRepository
import kotlinx.coroutines.launch

class AutenticacionViewModel(application: Application) : AndroidViewModel(application) {
    private val autenticacionRepositorty = AuthRepository()

    fun login(celular: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = autenticacionRepositorty.login(AccesoRequest(celular, password))
                if (response.isSuccessful) {
                    val token = response.body()?.jwtToken ?: ""
                    val prefs = getApplication<Application>().getSharedPreferences("auth", Context.MODE_PRIVATE)
                    prefs.edit().putString("jwt", token).apply()
                    onSuccess()
                } else {
                    onError("Credenciales incorrectas")
                }
            } catch (e: Exception) {
                onError("Error de red: ${e.localizedMessage}")
            }
        }
    }
}
