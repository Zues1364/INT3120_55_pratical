package com.example.flightSearch.ui

import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightSearch.FlightSearchApplication

object AppViewModelProvider {
    val factory = viewModelFactory {
        initializer {
            FlightSearchViewModel(
                GetSearchResults(flightSearchApplication().container.flightSearchRepository),
                flightSearchApplication().container.flightSearchRepository,
                flightSearchApplication().searchBarRepository
            )
        }
    }
}

fun CreationExtras.flightSearchApplication(): FlightSearchApplication =
    (this[androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FlightSearchApplication)
