package com.furfel.yarsa_frontend.responses

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import retrofit2.Response

data class SpringError(
    @SerializedName("timestamp") val timestamp: String,
    @SerializedName("status") val status: Int,
    @SerializedName("error") val error: String,
    @SerializedName("message") val message: String,
    @SerializedName("path") val path: String
)
{
    companion object {
        fun <T>fromResponse(ex:Response<T>): SpringError? {
            if(ex.code() >= 400)
                ex.errorBody()?.let {
                        return Gson()
                            .fromJson(it.charStream(), SpringError::class.java)
                }
            return null
        }
    }
}