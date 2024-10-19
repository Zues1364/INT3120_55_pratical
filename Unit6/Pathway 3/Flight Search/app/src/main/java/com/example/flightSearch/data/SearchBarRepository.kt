package com.example.flightSearch.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


class SearchBarRepository(private val dataStore: DataStore<Preferences>) {
    private companion object {
        val SEARCH_QUERY = stringPreferencesKey("search_query")
        const val TAG = "SearchBarRepository"
    }
    suspend fun saveSearchQueryToDataStore(query: String) {
        dataStore.edit { preferences ->
            preferences[SEARCH_QUERY] = query
        }
    }
    val searchQuery: Flow<String> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            }
            else {
                throw it
            }
        }
        .map { preference ->
        preference[SEARCH_QUERY] ?: ""
    }

}