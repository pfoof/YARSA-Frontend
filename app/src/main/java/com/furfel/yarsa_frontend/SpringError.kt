package com.furfel.yarsa_frontend

import com.google.gson.annotations.SerializedName

data class SpringError(
    @SerializedName("timestamp") val timestamp: String,
    @SerializedName("status") val status: Int,
    @SerializedName("error") val error: String,
    @SerializedName("message") val message: String,
    @SerializedName("path") val path: String
)
{}