package ru.shaa.main.domain.model

data class NationalizeApiModel(val response: List<ResponseNationalize>)

data class ResponseNationalize(
    val name: String,
    val country: String,
    val probability: Double
)
