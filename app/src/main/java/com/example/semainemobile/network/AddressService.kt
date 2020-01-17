package com.example.semainemobile.network

import com.example.semainemobile.network.data.AddressResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface AddressService {

    @GET("coverage/fr-idf/coord/{lon};{lat}")
    fun getAddress(@Path("lon") lon:String,
                   @Path("lat") lat:String,
                   @Header("Authorization") authorization:String): Call<AddressResult>
}