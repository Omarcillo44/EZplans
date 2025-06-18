package com.ezsoftware.ezplans.network

import com.ezsoftware.ezplans.models.DatosNuevoPlan
import com.ezsoftware.ezplans.models.DatosUsuarioEnPlan
import com.ezsoftware.ezplans.models.vistaDetallada.DatosVistaDetalladaPlan
import com.ezsoftware.ezplans.models.acceso.AccesoRequest
import com.ezsoftware.ezplans.models.acceso.AccesoResponse
import com.ezsoftware.ezplans.models.dashboard.datosDashboard
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
        @Header("Authorization") token: String
    ): Response<datosDashboard>


    @GET("/planes/vista_detallada")
    suspend fun obtenerVistaDetalladaPlan(
        @Query("idPlan") idPlan: Int,
        @Header("Authorization") token: String
    ): Response<DatosVistaDetalladaPlan>

    // Agrega este método a tu interfaz ApiService existente

    @POST("/planes/nuevo_plan")
    suspend fun crearNuevoPlan(
        @Body datosPlan: DatosNuevoPlan,
        @Header("Authorization") token: String
    ): Response<String>

    // Agrega este método a tu interfaz ApiService existente

    @GET("/planes/miembros")
    suspend fun obtenerMiembrosPlan(
        @Query("idPlan") idPlan: Int,
        @Header("Authorization") token: String
    ): Response<List<DatosUsuarioEnPlan>>
}