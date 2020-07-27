package com.furfel.yarsa_frontend.responses

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import retrofit2.Response

data class Success(@SerializedName("success") val success: String) {

    companion object {
        fun <T>fromResponse(ex: Response<T>):Success? {
            ex.raw().body()?.let {
                return Gson().fromJson(it.charStream(), Success::class.java)
            }
            return null
        }
    }
}