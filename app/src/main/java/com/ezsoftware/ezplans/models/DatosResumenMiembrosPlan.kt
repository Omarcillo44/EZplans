package com.ezsoftware.ezplans.models

data class DatosResumenMiembrosPlan(
    val idMiembro: Int,
    val nombreMiembro: String,
    val celularMiembro: String,
    val montoDeuda: String,
    val montoAportacion: String,
    val tieneDeuda: Boolean
)