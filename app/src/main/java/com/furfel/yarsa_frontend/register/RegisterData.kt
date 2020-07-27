package com.furfel.yarsa_frontend.register

import com.google.gson.annotations.SerializedName

data class RegisterData(
    @SerializedName("email") val email: String,
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String
) {}