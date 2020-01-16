package com.example.semainemobile.network

import com.example.semainemobile.network.data.AddressResult
import retrofit2.Call
import retrofit2.http.*

//interface AddressService {
//    @GET("coverage/{region}/coords/{lon};{lat}")
//    fun getAddress(@Path("region") region:String,
//                   @Path("lon") lon:Float,
//                   @Path("lat") lat:Float,
//                   @Header("Authorization") authorization:String): Call<AddressResult>
//}

// Marche pas

interface AddressService {

    @GET("coverage/fr-idf/coord/2.420498%3B48.851871")
    fun getAddress(@Header("Authorization") authorization:String): Call<AddressResult>
}

// Marche
//
//interface AddressService {
//
//    @GET("coverage/fr-idf/lines")
//    fun getAddress(@Header("Authorization") authorization:String): Call<AddressResult>
//}