package com.livmas.vtb_hack.connection

import retrofit2.Call

import retrofit2.http.Body
import retrofit2.http.POST


interface MainApi {

    @POST("/destination")

    fun sendLocation(@Body request: BankRequest): Call<List<BankResponse>>

}