// DatosVistaEditarPlan.kt
package com.ezsoftware.ezplans.models

import java.time.LocalDate

data class DatosVistaEditarPlan(
    val idPlan: Int,
    val titulo: String,
    val fecha: String,
    val detalles: String,
    val idAdministrador: Int
)