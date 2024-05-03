package ru.shaa.main.data.mapper

import ru.shaa.main.domain.model.DogApiModel
import ru.shaa.main.domain.model.ResponseNationalize
import ru.shaa.main.domain.model.dto.DogDTO
import ru.shaa.main.domain.model.dto.NationalizeDTO

fun DogDTO.toDogModel() = DogApiModel(
    url = message
)

fun NationalizeDTO.toResponseNationalize() = ResponseNationalize(
    name = name,
    country = country.first().id,
    probability = country.first().probability
)


//fun CustomDTO.toCustomModel() = CustomApiModel(
//    json = json
//)