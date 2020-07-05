package com.furfel.yarsa_frontend.models

import com.google.gson.annotations.SerializedName

class UserProfileModel {
    @SerializedName("id")
    val id: String = ""

    @SerializedName("username")
    val username: String = ""

    @SerializedName("entries")
    val entries: List<EntryModel> = listOf()

}