package com.example.domain.models

sealed class ErrorEntity {

    sealed class ApiError : ErrorEntity() {

        data object Network : ErrorEntity()

        class BadRequest(val errorMessage: String) : ErrorEntity()

        data object Unknown : ErrorEntity()

        data object ResponseNull : ErrorEntity()

    }

}