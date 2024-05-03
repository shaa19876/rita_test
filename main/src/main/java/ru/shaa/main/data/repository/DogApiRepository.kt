package ru.shaa.main.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import ru.shaa.main.data.api.DogApi
import ru.shaa.main.data.mapper.toDogModel
import ru.shaa.main.data.repository.util.RequestResult
import ru.shaa.main.domain.model.DogApiModel

class DogApiRepository(
    private val api: DogApi
) : MainRepository<DogApiModel> {
    override fun getData(request: String?): Flow<RequestResult<DogApiModel>> {
        val start = flowOf(RequestResult.Loading<DogApiModel>())

        val response = flow {
            emit(api.getData())
        }.map { result ->
            if (result.isSuccess) {
                result.getOrThrow()
                    .toDogModel()
                    .let { RequestResult.Success(it) }
            } else {
                RequestResult.Error()
            }
        }

        return merge(start, response)
    }
}