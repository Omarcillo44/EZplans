package com.ezsoftware.ezplans.models

import com.fasterxml.jackson.annotation.JsonProperty

data class DatosMiembrosNuevoPlan(
    @JsonProperty("idUsuario") val idUsuario: Int,
    @JsonProperty("administrador") val administrador: Boolean
)