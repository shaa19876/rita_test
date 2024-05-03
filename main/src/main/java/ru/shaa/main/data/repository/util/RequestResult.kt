package ru.shaa.main.data.repository.util

sealed class RequestResult<T> {
    class Success<T>(val data: T) : RequestResult<T>()
    class Error<T> : RequestResult<T>()
    class Loading<T> : RequestResult<T>()
}
