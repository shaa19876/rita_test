package ru.shaa.main.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import ru.shaa.main.data.api.NationalizeApi
import ru.shaa.main.data.mapper.toResponseNationalize
import ru.shaa.main.data.repository.util.RequestResult
import ru.shaa.main.domain.model.NationalizeApiModel

class NationalizeApiRepository(
    private val api: NationalizeApi
) : MainRepository<NationalizeApiModel> {
    override fun getData(request: String?): Flow<RequestResult<NationalizeApiModel>> {
        val start = flowOf(RequestResult.Loading<NationalizeApiModel>())

        val response = flow {
            emit(api.getData(request!!.split(", ", limit = 10)))
        }.map { result ->
            if (result.isSuccess) {
                result.getOrThrow()
                    .map { it.toResponseNationalize() }
                    .let { RequestResult.Success(NationalizeApiModel(it)) }
            } else {
                RequestResult.Error()
            }
        }

        return merge(start, response)
    }
}
