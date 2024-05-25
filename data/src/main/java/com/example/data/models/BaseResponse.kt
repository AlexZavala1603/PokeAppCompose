package com.example.data.models

import com.google.gson.annotations.SerializedName

data class BaseResponse<out T>(
    @SerializedName("count")
    val count: Int? = 0,
    @SerializedName("next")
    val next: String? = "",
    @SerializedName("previous")
    val previous: String? = "",
    @SerializedName("results")
    val data: T? = null
)
