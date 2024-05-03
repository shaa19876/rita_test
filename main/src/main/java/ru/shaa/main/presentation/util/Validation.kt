package ru.shaa.main.presentation.util

fun String.validationNames(): Boolean {
    val regex = "[a-zA-Z]+(?:, ?[a-zA-Z]+)*".toRegex()
    return regex.matches(this.lowercase().trim())
}

fun String.validationUrl(): Boolean {
    val regex = "^(http(s)?:\\/\\/)?([\\w-]+.)+[\\w]+(\\/[\\w- ./?%&=]*)?\$".toRegex()
    return regex.matches(this.lowercase().trim())
}