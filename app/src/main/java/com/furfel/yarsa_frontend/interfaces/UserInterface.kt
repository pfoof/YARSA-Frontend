package com.furfel.yarsa_frontend.interfaces

import retrofit2.Call
import retrofit2.http.*

interface UserInterface {
    @POST("user/register")
    @FormUrlEncoded
    fun registerUser(@Field("username") username: String, @Field("email") email: String, @Field("password") password: String): Call<String>

    @POST("user/login")
    @FormUrlEncoded
    fun loginUser(@Field("username") username: String, @Field("password") password: String): Call<String>
}