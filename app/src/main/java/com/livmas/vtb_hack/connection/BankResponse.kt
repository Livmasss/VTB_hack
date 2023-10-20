package com.livmas.vtb_hack.connection


data class BankResponse(
    val address: String?,
    val latitude: Double,
    val longitude: Double,
    val criteria: String,
    val type: Boolean,
    val workload: Float,
    val id: Int
)

