package ru.shaa.main.data.repository

import kotlinx.coroutines.flow.Flow
import ru.shaa.main.data.repository.util.RequestResult

interface MainRepository <T> {
    fun getData(request: String? = null): Flow<RequestResult<T>>
}