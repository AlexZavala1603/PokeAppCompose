package com.example.data.models

import com.google.gson.annotations.SerializedName

data class TypeInfo(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)
