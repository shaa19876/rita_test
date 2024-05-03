package ru.shaa.main.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.shaa.main.domain.model.dto.NationalizeDTO

interface NationalizeApi {

    @GET("/")
    suspend fun getData(
        @Query("name[]") name: List<String>
    ): Result<List<NationalizeDTO>>

}