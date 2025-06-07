package com.ezsoftware.ezplans.network

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object RetrofitInstance {

    val url: String = "https://9900-2806-2f0-90a0-63fc-6425-36c0-9f2-f53a.ngrok-free.app/"

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
