package com.ezsoftware.ezplans.models

data class DatosDeudasPorPlan(
    val idDeuda: Int,
    val nombreDeudor: String,
    val nombreAcreedor: String,
    val tituloActividad: String,
    val montoDeuda: String
)