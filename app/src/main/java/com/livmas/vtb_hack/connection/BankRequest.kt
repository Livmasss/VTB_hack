package com.livmas.vtb_hack.connection

import com.google.gson.annotations.SerializedName

data class BankRequest(
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("criteria")
    val criteria: String,
    @SerializedName("onlyDepartments")
    val onlyDepartments: Boolean,
    @SerializedName("time")
    val time: String
)
