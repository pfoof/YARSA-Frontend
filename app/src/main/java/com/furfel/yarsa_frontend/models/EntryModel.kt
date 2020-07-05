package com.furfel.yarsa_frontend.models

import com.google.gson.annotations.SerializedName

open class EntryModel {

    @SerializedName("stars")
    val rating: Int = 0

    @SerializedName("book")
    val book: BookModel = BookModel()

}