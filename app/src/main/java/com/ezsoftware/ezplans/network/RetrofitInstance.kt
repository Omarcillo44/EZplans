package com.ezsoftware.ezplans.network

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object RetrofitInstance {
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl( //Dirección del localhost, cambiala por la tuya
                "https://05c5-2806-2f0-90a0-63fc-68eb-4626-dc2-1745.ngrok-free.app"
            )
            .addConverterFactory(JacksonConverterFactory.create(
                jacksonObjectMapper().registerKotlinModule()
            ))
            .build()
            .create(ApiService::class.java)
    }
}
