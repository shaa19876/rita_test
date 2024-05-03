package ru.shaa.main.presentation.util

sealed class MainState {
    data object None: MainState()
    data object Loading : MainState()
    data object Error : MainState()
    class Success <T> (val data: T): MainState()
}