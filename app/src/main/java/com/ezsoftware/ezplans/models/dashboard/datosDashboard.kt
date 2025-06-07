package com.ezsoftware.ezplans.models.dashboard

data class datosDashboard(
    val datosResumen: datosResumen,
    val datosPlanes: List<datosPlan>
)
