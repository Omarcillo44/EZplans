package com.ezsoftware.ezplans.network

import com.ezsoftware.ezplans.models.acceso.AccesoRequest
import com.ezsoftware.ezplans.models.acceso.AccesoResponse
import com.ezsoftware.ezplans.models.dashboard.datosDashboard
import com.ezsoftware.ezplans.models.user.DatosUsuarioRequest
import com.ezsoftware.ezplans.models.user.DatosUsuarioResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("/login")
    suspend fun login(@Body bodyRequest: AccesoRequest): Response<AccesoResponse>

    @GET("/usuario/datos")
    suspend fun obtenerDatosUsuario(
        @Query("celularUsuario") celularUsuario: String,
        @Header("Authorization") token: String
    ): Response<DatosUsuarioResponse>

    @GET("/dashboard")
    suspend fun obtenerDatosDashboard(
        @Query("idUsuario") idUsuario: Int,
        @Query("soloCompletos") soloCompletos: Boolean?,
        @Query("esAdmin") esAdmin: Boolean?,
        @Header("Authorization") token: String  // Agregar el token de autenticación
    ): Response<datosDashboard>

}