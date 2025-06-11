package com.ezsoftware.ezplans.network

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object RetrofitInstance {

    val url: String = "https://stag-improved-wildcat.ngrok-free.app/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl( //Dirección del localhost, cambiala por la tuya
                url
            )
            .addConverterFactory(JacksonConverterFactory.create(
                jacksonObjectMapper().registerKotlinModule()
            ))
            .build()
            .create(ApiService::class.java)
    }
}
