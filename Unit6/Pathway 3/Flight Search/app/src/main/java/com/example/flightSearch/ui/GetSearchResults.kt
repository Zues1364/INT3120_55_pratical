package com.example.flightSearch.ui

import com.example.flightSearch.data.Airport
import com.example.flightSearch.data.FlightSearchRepository
import kotlinx.coroutines.flow.Flow

class GetSearchResults(private val flightSearchRepository: FlightSearchRepository) {
    sealed interface Result {
        object Error : Result
        data class Success(val data: Flow<List<Airport>>) : Result
    }

    operator fun invoke(query: String): Result = try {
        val airports = flightSearchRepository.getAllAirports(query)
        Result.Success(airports)
    } catch (e: Exception) {
        Result.Error
    }
}


