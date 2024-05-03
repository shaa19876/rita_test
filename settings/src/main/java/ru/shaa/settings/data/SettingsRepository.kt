package ru.shaa.settings.data

import ru.shaa.core.SharedPref
import javax.inject.Inject

class SettingsRepository @Inject constructor(
    private val sharedPref: SharedPref
) {
    private val apiKey = "API"

    fun save(newApiKey: String) {
        sharedPref.save(apiKey, newApiKey)
    }

    fun load() : String? {
        return sharedPref.load(apiKey)
    }

}