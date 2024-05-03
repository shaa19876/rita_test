package ru.shaa.core

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPref @Inject constructor(@ApplicationContext context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        context.getString(R.string.preference_file_key),
        Context.MODE_PRIVATE
    )

    fun save(key: String, value: String) {
        with( sharedPreferences.edit()) {
            putString(key, value)
            apply()
        }
    }

    fun load(key: String) : String? {
        return sharedPreferences.getString(key, null)
    }
}