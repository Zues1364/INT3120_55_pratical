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
package com.example.flightSearch.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flightSearch.data.Airport
import com.example.flightSearch.data.Favorite
import com.example.flightSearch.data.Flight
import com.example.flightSearch.data.FlightSearchRepository
import com.example.flightSearch.data.SearchBarRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean


class FlightSearchViewModel(
    private val getSearchResults: GetSearchResults,
    private val flightSearchRepository: FlightSearchRepository,
    private val searchBarRepository: SearchBarRepository
) : ViewModel() {
    companion object {
        private const val TIME_OUT_MILLIS = 5_00L
    }

    sealed interface FlightSearchUiState {
        data class IdleScreen(val favoriteList: MutableList<Flight>) : FlightSearchUiState
        object LoadingScreen : FlightSearchUiState
        object ErrorScreen : FlightSearchUiState
        object EmptyScreen : FlightSearchUiState
        data class SearchResultFetched(val data: Flow<List<Airport>>) : FlightSearchUiState
        data class SearchFlightClicked(
            val departureAirport: Airport,
            val flightList: List<Flight> = emptyList()
        ) : FlightSearchUiState
    }

    sealed interface SearchBarUiState {
        object Idle : SearchBarUiState
        object EmptyActive : SearchBarUiState
        object WithInputActive : SearchBarUiState
    }

    private val _flightSearchUiState: MutableStateFlow<FlightSearchUiState> =
        MutableStateFlow(
            FlightSearchUiState.IdleScreen(
                mutableListOf()
            )
        )
    val flightSearchUiState: StateFlow<FlightSearchUiState> = _flightSearchUiState

    private val _searchBarUiState: MutableStateFlow<SearchBarUiState> =
        MutableStateFlow(SearchBarUiState.Idle)
    val searchBarUiState: StateFlow<SearchBarUiState> = _searchBarUiState

    private suspend fun getFavouriteFlights() {
        flightSearchRepository.getAllFavorites().collect { favorites ->
            val favouritesList: MutableList<Flight> = mutableListOf<Flight>()
            for (favorite in favorites) {
                val departureAirport =
                    flightSearchRepository.getAirportByIataCode(favorite.departureCode)
                        .firstOrNull()
                val destinationAirport =
                    flightSearchRepository.getAirportByIataCode(favorite.destinationCode)
                        .firstOrNull()
                if (departureAirport != null && destinationAirport != null) {
                    favouritesList.add(
                        Flight(
                            departureAirport = departureAirport,
                            destinationAirport = destinationAirport,
                            favorite = true
                        )
                    )
                }
            }
            _flightSearchUiState.update {
                FlightSearchUiState.IdleScreen(favoriteList = favouritesList)
            }
        }
    }

    private val _searchQuery: MutableStateFlow<String> = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery
    private val isInitialized = AtomicBoolean(false)

    fun initialize() {
        if (isInitialized.compareAndSet(false, true)) {
            viewModelScope.launch {
                loadSearchQuery()
                searchQuery.debounce(timeoutMillis = TIME_OUT_MILLIS)
                    .collectLatest { input ->
                        if (input.blankOrEmpty()) {
                            getFavouriteFlights()
                            return@collectLatest
                        }
                        when (val result = getSearchResults(input)) {
                            is GetSearchResults.Result.Success -> {
                                _flightSearchUiState.update {
                                    FlightSearchUiState.SearchResultFetched(result.data)
                                }
                                result.data.collect { airports ->
                                    if (airports.isEmpty()) {
                                        _flightSearchUiState.update { FlightSearchUiState.EmptyScreen }
                                    }
                                }

                            }

                            is GetSearchResults.Result.Error -> {
                                _flightSearchUiState.update { FlightSearchUiState.ErrorScreen }
                            }


                        }
                    }
            }
        }
    }

    private suspend fun loadSearchQuery() {
        val searchQuery = searchBarRepository.searchQuery.firstOrNull()
        Log.d("loadSearchQuery", "searchQuery: $searchQuery")
        _searchQuery.update {
            searchQuery ?: ""
        }
        if (searchQuery != null) {
            _flightSearchUiState.update {
                FlightSearchUiState.LoadingScreen
            }
        }
    }

    private fun String.blankOrEmpty() = this.isBlank() || this.isEmpty()

    fun updateSearchQuery(query: String) {
        _searchQuery.update { query }
        viewModelScope.launch {
            searchBarRepository.saveSearchQueryToDataStore(query)
        }
        activateSearchBar()
        if (query.blankOrEmpty().not()) {
            _flightSearchUiState.update {
                FlightSearchUiState.LoadingScreen
            }
        }

    }

    fun searchBarActivated() {
        activateSearchBar()
    }

    fun searchBarDeactivated() {
        _searchQuery.update { "" }
        _searchBarUiState.update { SearchBarUiState.Idle }
    }

    fun clearSearchQuery() {
        _searchQuery.update { "" }
        _searchBarUiState.update { SearchBarUiState.EmptyActive }
    }

    private fun activateSearchBar() {
        if (searchQuery.value.blankOrEmpty().not()) {
            _searchBarUiState.update { SearchBarUiState.WithInputActive }
        } else {
            _searchBarUiState.update { SearchBarUiState.EmptyActive }
        }
    }

    private suspend fun getFavorite(departureId: String, destinationId: String): Favorite? {
        return flightSearchRepository
            .getFavouriteByIataCode(departureId, destinationId).firstOrNull()
    }

    fun onSearchClicked(departureAirport: Airport) {
        viewModelScope.launch {
            flightSearchRepository.getDestinationAirports(departureAirport.name)
                .collect { airports ->
                    val flightList: MutableList<Flight> = mutableListOf()
                    for (airport in airports) {
                        if (getFavorite(departureAirport.iataName, airport.iataName) == null) {
                            flightList.add(Flight(departureAirport, airport))
                        } else {
                            flightList.add(Flight(departureAirport, airport, true))
                        }
                    }
                    _flightSearchUiState.update {
                        FlightSearchUiState.SearchFlightClicked(
                            departureAirport = departureAirport,
                            flightList = flightList
                        )
                    }
                }

        }
    }

    fun onFavoriteClicked(flight: Flight) {
        viewModelScope.launch {
            val favorite = getFavorite(
                flight.departureAirport.iataName, flight.destinationAirport.iataName
            )
            if (favorite == null) {
                flight.favorite = true
                flightSearchRepository.insertFavourite(
                    Favorite(
                        departureCode = flight.departureAirport.iataName,
                        destinationCode = flight.destinationAirport.iataName
                    )
                )
            } else {
                flightSearchRepository.deleteFavouriteByIataCode(
                    Favorite(
                        departureCode = flight.departureAirport.iataName,
                        destinationCode = flight.destinationAirport.iataName
                    )
                    //favorite
                )
                flight.favorite = false
            }


        }
    }

}

