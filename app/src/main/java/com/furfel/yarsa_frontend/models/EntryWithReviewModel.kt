package com.furfel.yarsa_frontend.models

import com.google.gson.annotations.SerializedName

class EntryWithReviewModel: EntryModel() {

    @SerializedName("review")
    val review: String = ""

}