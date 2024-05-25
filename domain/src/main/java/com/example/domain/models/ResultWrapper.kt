package com.example.domain.models

sealed class ResultWrapper<out L, out R> {
    data class Left<out L>(val l: L) : ResultWrapper<L, Nothing>()

    data class Right<out R>(val r: R) : ResultWrapper<Nothing, R>()

    fun <T> fold(fnL: (L) -> T, fnR: (R) -> T): T {
        return when (this) {
            is Left -> fnL(l)
            is Right -> fnR(r)
        }
    }
}
