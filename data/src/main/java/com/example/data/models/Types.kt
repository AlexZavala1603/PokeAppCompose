package com.example.data.models

import com.google.gson.annotations.SerializedName

data class Types(
    @SerializedName("slot") val slot: Int,
    @SerializedName("type") val type: TypeInfo
)
