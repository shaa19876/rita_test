package ru.shaa.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.shaa.settings.data.SettingsRepository
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: SettingsRepository
) :
    ViewModel() {

    private var _api = MutableStateFlow("Dog api")
    val api = _api.asStateFlow()

    fun changeApi(newApi: String) {
        _api.value = newApi
        saveSettings()
    }

    private fun saveSettings() {
            repository.save(api.value)
    }

    private fun loadSettings() {
            repository.load()?.let { _api.value = it }
    }

    init {
        loadSettings()
    }
}