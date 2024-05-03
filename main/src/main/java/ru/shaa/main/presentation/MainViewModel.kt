package ru.shaa.main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.shaa.core.SharedPref
import ru.shaa.main.data.repository.MainRepository
import ru.shaa.main.data.repository.MainRepositoryFactory
import ru.shaa.main.presentation.mapper.toState
import ru.shaa.main.presentation.util.BaseApi
import ru.shaa.main.presentation.util.MainState
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sharedPref: SharedPref
) : ViewModel() {

    private val repositoryCache: MutableMap<String, MainRepository<*>> = mutableMapOf()

    private var _currentApi = MutableStateFlow<BaseApi>(BaseApi.Dog())
    val currentApi = _currentApi.asStateFlow()

    private var _state = MutableStateFlow<MainState>(MainState.None)
    val state = _state.asStateFlow()

    fun getCurrentApi() {
        _currentApi.value = when (sharedPref.load("API")) {
            currentApi.value.name -> return
            "Nationalize api" -> BaseApi.Nationalize()
            "Custom api" -> BaseApi.Custom()
            else -> BaseApi.Dog()
        }
        _state.value = MainState.None

    }

    fun fetch(request: String? = null) {

        if (currentApi.value is BaseApi.Custom) {
            _currentApi.value.url = request!!
        }

        val repo = repositoryCache.getOrPut(currentApi.value.name) {
            MainRepositoryFactory().create(currentApi.value)
        }
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repo.getData(request).collect {
                    _state.value = it.toState()
                }
            }
        }
    }
}