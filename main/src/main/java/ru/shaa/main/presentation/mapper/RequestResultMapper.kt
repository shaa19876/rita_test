package ru.shaa.main.presentation.mapper

import ru.shaa.main.data.repository.util.RequestResult
import ru.shaa.main.presentation.util.MainState

fun RequestResult<*>.toState(): MainState {
    return when(this) {
        is RequestResult.Loading -> MainState.Loading
        is RequestResult.Error -> MainState.Error
        is RequestResult.Success -> MainState.Success(data)
    }
}