// DatosEditarPlan.kt
package com.ezsoftware.ezplans.models

import java.time.LocalDate

data class DatosEditarPlan(
    val idPlan: Int,
    val titulo: String,
    val detalles: String,
    val fecha: String
)