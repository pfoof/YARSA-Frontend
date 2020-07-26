package com.furfel.yarsa_frontend.interfaces

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface UserInterface {
    @POST("user/register")
    fun registerUser(@Query("username") username: String, @Query("email") email: String, @Query("password") password: String): Call<String>

    @POST("user/login")
    fun loginUser(@Query("username") username: String, @Query("password") password: String): Call<String>
}