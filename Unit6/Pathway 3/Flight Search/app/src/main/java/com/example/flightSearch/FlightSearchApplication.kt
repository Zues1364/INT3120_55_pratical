/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.flightSearch

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.flightSearch.data.AppContainer
import com.example.flightSearch.data.AppDataContainer
import com.example.flightSearch.data.SearchBarRepository

private const val SEARCH_PREFERENCE_NAME = "search_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = SEARCH_PREFERENCE_NAME
)

class FlightSearchApplication: Application() {
    lateinit var container: AppContainer
    lateinit var searchBarRepository: SearchBarRepository
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
        searchBarRepository = SearchBarRepository(dataStore)
    }
}
