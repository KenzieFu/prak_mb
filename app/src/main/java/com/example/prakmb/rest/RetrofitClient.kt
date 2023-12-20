package com.example.prakmb.rest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Create a RetrofitClient object with the API BASE URL and Gson Converter
object RetrofitClient {
    // Set Up this url based on your environment
    // http://10.0.2.2/api/  192.168.1.45
    private const val BASE_URL = "http://10.0.2.2/api/" // Fill the ip from your local IP Address

    val instance: API by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(API::class.java)
    }
}