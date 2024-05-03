package ru.shaa.main.data.repository

import ru.shaa.main.data.api.createApi
import ru.shaa.main.presentation.util.BaseApi

class MainRepositoryFactory {
    fun create(apiType: BaseApi): MainRepository<*> {
        return when(apiType) {
            is BaseApi.Dog -> DogApiRepository(createApi(apiType.url))
            is BaseApi.Nationalize -> NationalizeApiRepository(createApi(apiType.url))
            is BaseApi.Custom -> CustomApiRepository()
        }
    }
}