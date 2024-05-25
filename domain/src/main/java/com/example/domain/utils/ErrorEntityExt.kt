package com.example.domain.utils

import com.example.domain.models.ErrorEntity

fun ErrorEntity.getErrorMessage(): String {
    return when (this) {
        is ErrorEntity.ApiError.BadRequest -> "The request was invalid or cannot be served."
        ErrorEntity.ApiError.Network -> "Network connection error. Please check your connection."
        ErrorEntity.ApiError.ResponseNull -> "The server response was null."
        ErrorEntity.ApiError.Unknown -> "An unknown error occurred. Please try again."
    }
}