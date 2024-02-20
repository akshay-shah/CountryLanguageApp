package com.example.countrylanguage.presentation

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val msg: String) : Result<Nothing>()
    data object Loading : Result<Nothing>()
}