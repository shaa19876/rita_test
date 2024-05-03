package ru.shaa.main.domain.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NationalizeDTO(
    val count: Int,
    val name: String,
    val country: List<Country>,
)

@Serializable
data class Country(
    @SerialName("country_id")
    val id: String,
    val probability: Double
)
