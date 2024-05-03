package ru.shaa.main.domain.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class DogDTO(
    val message: String,
    val status: String,
)
