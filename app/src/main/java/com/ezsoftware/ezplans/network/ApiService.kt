package com.ezsoftware.ezplans.network

import com.ezsoftware.ezplans.models.acceso.AccesoRequest
import com.ezsoftware.ezplans.models.acceso.AccesoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/login")
    suspend fun login(@Body request: AccesoRequest): Response<AccesoResponse>
}