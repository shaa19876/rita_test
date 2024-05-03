package ru.shaa.main.data.api

import retrofit2.http.GET
import ru.shaa.main.domain.model.dto.DogDTO

interface DogApi {

    @GET("random")
    suspend fun getData(): Result<DogDTO>

}